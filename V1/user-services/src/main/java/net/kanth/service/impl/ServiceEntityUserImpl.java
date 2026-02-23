package net.kanth.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kanth.entity.EntityAddress;
import net.kanth.entity.EntityOrganization;
import net.kanth.entity.EntityRole;
import net.kanth.entity.EntityUser;
import net.kanth.entity.EntityUserRole;
import net.kanth.entity.UserRoleId;
import net.kanth.exceptions.BadRequestException;
import net.kanth.exceptions.ResourceNotFoundException;
import net.kanth.payload.PayloadMessage;
import net.kanth.payload.PayloadUser;
import net.kanth.repo.RepoEntityOrganization;
import net.kanth.repo.RepoEntityRole;
import net.kanth.repo.RepoEntityUser;
import net.kanth.repo.RepoEntityUserRole;
import net.kanth.service.ServiceEntityOrganization;
import net.kanth.service.ServiceEntityUser;
import net.kanth.util.UtilsNullProperties;

import java.util.Arrays;
import java.util.HashSet;
@Slf4j
@AllArgsConstructor
@Service
public class ServiceEntityUserImpl implements ServiceEntityUser {

	private RepoEntityUser repoEntityUser;
	private RepoEntityRole repoEntityRole;
	private RepoEntityUserRole repoEntityUserRole;
	private RepoEntityOrganization repoEntityOrganization;
	private ModelMapper modelMapper;
	private ServiceEntityOrganization serviceEntityOrganization;
	private PasswordEncoder encoder;
	
	@Override
	public PayloadUser saveUser(PayloadUser payload) {	
		Optional.ofNullable(payload.getAddress()).orElseThrow(() -> new BadRequestException("Address"));
		log.info("Payload Info {}", payload);	
        EntityUser entityUser =  modelMapper.map(payload, EntityUser.class);    
        EntityOrganization organization = serviceEntityOrganization.findByOrganizationName(payload.getOrganization().getOrganizationName());
        entityUser.setOrganization(organization);
        entityUser.setPassword(encoder.encode(payload.getPassword()));
        EntityUser savedUser = repoEntityUser.save(entityUser); 
        
        /* Default Role To User*/
        Optional<EntityRole> optional = repoEntityRole.findByRoleName("USER");
        if(optional.isPresent()) {
        	EntityRole role = optional.get();
        	UserRoleId roleId = new UserRoleId();
        	roleId.setRoleId(role.getId());
        	roleId.setUserId(savedUser.getId());
        	
        	EntityUserRole e1 = new EntityUserRole();
        	e1.setRole(role);
        	e1.setUser(savedUser);
        	e1.setUserRole(roleId);
        	repoEntityUserRole.save(e1);
        	if (savedUser.getRoles() == null) {
        	    savedUser.setRoles(new HashSet<>(Arrays.asList(e1)));
        	}
        }
        
        log.info("Entity Info {}",entityUser);
		return modelMapper.map(savedUser, PayloadUser.class);
	}

	@Override
	public List<PayloadUser> findAll() {
		List<EntityUser> objUserList = repoEntityUser.findAll();	
		List<PayloadUser> payLoadList =  objUserList.
				stream().map(p ->  modelMapper.map(p, PayloadUser.class))
				.collect(Collectors.toList());
		return payLoadList;
	}

	@Override
	public PayloadUser findById(UUID uuid) {
		log.info("Id of the user {}",uuid);
		Optional<EntityUser> optional = repoEntityUser.findById(uuid);
		if(optional.isEmpty()) {
			throw new ResourceNotFoundException("User","Id",uuid+"");
		}
		
		PayloadUser payload = modelMapper.map(optional.get(), PayloadUser.class);
		log.info("User info {}",payload);
		return payload;
	}

	@Override
	public PayloadUser findByUsername(String username) {
		log.info("User to found {}",username);
		
		Optional<EntityUser> optional = repoEntityUser.findByUsername(username);
		if(optional.isEmpty()) {
			throw new ResourceNotFoundException("User","username",username);
		}
		
		log.info("User info {}",optional.get());
		return modelMapper.map(optional.get(), PayloadUser.class);
	}
	
	

	 @Override
	 public Object deleteById(UUID id) {
		if(!repoEntityUser.existsById(id)) {
			 throw new ResourceNotFoundException("User", "Id", id.toString());
		}
		repoEntityUser.deleteById(id);
		return new PayloadMessage("User Deleted", "User with id '"+id+ "' deleted successfully!", LocalDate.now()+"");
	 }

	
	 @Override
	 public void testToSaveUserRoles(String userId) {
	    	try {
	    		EntityUser userObj = repoEntityUser.findById(UUID.fromString(userId)).orElseThrow();
	    		log.info("User Details {} ",userObj);	

				/**************** SECOND PART *******************/
	    		Optional<EntityRole> userRoleOptional = repoEntityRole.findByRoleName("USER");
	    		if(userRoleOptional.isEmpty()) {
	    			throw new ResourceNotFoundException("role", "rolename", "USER");
	    		}
	    		
	    		EntityRole userRole = userRoleOptional.get();
				UserRoleId idKeyUser = new UserRoleId(userObj.getId(), userRole.getId());
				EntityUserRole userUserRole = new EntityUserRole();
				userUserRole.setUser(userObj);
				userUserRole.setRole(userRole);
				userUserRole.setUserRole(idKeyUser);
				userObj.getRoles().addAll(List.of(userUserRole));
				userRole.getUserRoles().addAll(List.of(userUserRole));
				
				repoEntityUser.save(userObj);
	    	}catch(Exception ex) { ex.printStackTrace(); }
	    	
	    }
	 
	

	 @Override
	 public PayloadUser updateUserByIdV2(UUID uuid, PayloadUser payload){
		 
			EntityUser user =  repoEntityUser.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User","id",uuid+""));
	        BeanUtils.copyProperties(payload, user, UtilsNullProperties.getNullPropertyNames(payload));
	        if (payload.getAddress() != null) {

	            if (user.getAddress() == null) {
	                user.setAddress(new EntityAddress());
	            }

	            BeanUtils.copyProperties(payload.getAddress(),
	                    user.getAddress(),
	                    UtilsNullProperties.getNullPropertyNames(payload.getAddress()));     
	        }
	        if(user.getOrganization() == null) {
            	user.setOrganization(new EntityOrganization());
            }
            
            if(payload.getOrganization()!=null && StringUtils.hasText(payload.getOrganization().getOrganizationName())) {
	            Optional<EntityOrganization> optional =  repoEntityOrganization.findByOrganizationName(payload.getOrganization().getOrganizationName());
	            if(optional.isPresent()) {
	            	user.setOrganization(optional.get());
	            	optional.get().setUsers(List.of(user));
	            	repoEntityOrganization.save(optional.get());
	            }
            }
            
            if(StringUtils.hasText(payload.getPassword())) {
            	user.setPassword(encoder.encode(payload.getPassword()));
            }
            
	        EntityUser savedUser = repoEntityUser.save(user);
	        
	        return modelMapper.map(savedUser, PayloadUser.class);
	 }	 
	 
	
	 @Override
	 public PayloadUser updateUserByIdV3(UUID uuid, PayloadUser payload){
		 
		 try {
			EntityUser user =  repoEntityUser.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("User","id",uuid+""));
	        BeanUtils.copyProperties(payload, user, UtilsNullProperties.getNullPropertyNames(payload));
	        if (payload.getAddress() != null) {

	            if (user.getAddress() == null) {
	                user.setAddress(new EntityAddress());
	            }

	            BeanUtils.copyProperties(payload.getAddress(),
	                    user.getAddress(),
	                    UtilsNullProperties.getNullPropertyNames(payload.getAddress()));     
	        }
	        if(user.getOrganization() == null) {
            	user.setOrganization(new EntityOrganization());
            }
            
            if(payload.getOrganization()!=null && StringUtils.hasText(payload.getOrganization().getOrganizationName())) {
	            Optional<EntityOrganization> optional =  repoEntityOrganization.findByOrganizationName(payload.getOrganization().getOrganizationName());
	            if(optional.isPresent()) {
	            	user.setOrganization(optional.get());
	            	optional.get().setUsers(List.of(user));
	            	repoEntityOrganization.save(optional.get());
	            }
            }
            
            if(StringUtils.hasText(payload.getPassword())) {
            	user.setPassword(encoder.encode(payload.getPassword()));
            }
	        EntityUser savedUser = repoEntityUser.save(user);
	        return modelMapper.map(savedUser, PayloadUser.class);


		 }catch (Exception e) { e.printStackTrace(); }
	        return null;
	 }	 
	 
}
