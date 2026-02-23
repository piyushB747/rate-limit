package net.kanth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kanth.entity.EntityOrganization;
import net.kanth.entity.EntityRole;
import net.kanth.entity.EntityUser;
import net.kanth.entity.EntityUserRole;
import net.kanth.entity.UserRoleId;
import net.kanth.enums.EnumCountry;
import net.kanth.enums.EnumOrganizationType;
import net.kanth.exceptions.ResourceNotFoundException;
import net.kanth.payload.PayloadRole;
import net.kanth.repo.RepoEntityOrganization;
import net.kanth.repo.RepoEntityRole;
import net.kanth.repo.RepoEntityUser;
import net.kanth.repo.RepoEntityUserRole;
import net.kanth.service.ServiceEntityRole;


@AllArgsConstructor
@Slf4j
@Service
public class ServiceEntityRoleImpl implements ServiceEntityRole{
	
	private RepoEntityRole repoEntityRole;
	private RepoEntityUser repoEntityUser;
	private RepoEntityUserRole repoEntityUserRole;
	private RepoEntityOrganization repoEntityOrganization;
	
	@Override
	public List<PayloadRole> findAllRoles() {	
		return repoEntityRole.findAllRolesOnly();
	}
	
	@Override
	public EntityRole findById(UUID uuid) {
		log.info("Id of the user {}",uuid);	
		Optional<EntityRole> payload  = repoEntityRole.findById(uuid);	
		if(payload.isEmpty()) {
			throw new ResourceNotFoundException("role", "id", uuid.toString());
		}
		return payload.get();
	}
	
	@Override
	public Object findUsersByRoleName(String roleName) {
		
		return repoEntityUser.findByRoleName(roleName);
	}
	
	@Override  //OUTDATED METHOD
	public List<EntityRole> findAll() {	
		log.info("Fetching all roles {}",UUID.randomUUID());
		return repoEntityRole.findAll();
	}

	 @Transactional
	 @Override
	 public void assignUserRole(String userId,String roleName) {

	     EntityUser user = repoEntityUser.findById(UUID.fromString(userId))
	             .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

	     EntityRole role = repoEntityRole.findByRoleName(roleName)
	             .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", "USER"));

	     UserRoleId id = new UserRoleId(user.getId(), role.getId());

	     // check duplicate entry
	     boolean alreadyExists = repoEntityUserRole.existsById(id);
	     if (alreadyExists) {
	         return; // or throw exception "Role already assigned"
	     }

	     EntityUserRole userRole = new EntityUserRole();
	     userRole.setUserRole(id);
	     userRole.setUser(user);
	     userRole.setRole(role);

	     repoEntityUserRole.save(userRole);
	 }

	 @Transactional
	 @Override
	 public void removeUserRole(String userId, String roleName) {

	     EntityUser user = repoEntityUser.findById(UUID.fromString(userId))
	             .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

	     EntityRole role = repoEntityRole.findByRoleName(roleName)
	             .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", roleName));

	     UserRoleId id = new UserRoleId(user.getId(), role.getId());

	     if (!repoEntityUserRole.existsById(id)) {
	         throw new ResourceNotFoundException("UserRole", "id", id.toString());
	     }

	     repoEntityUserRole.deleteRoleFromUser(UUID.fromString(userId),roleName);
	     log.info("Role Deleted Successfully! {}",user.getFirstName()+" "+user.getLastName());
	 }
	 
	 @Transactional
	 @Override
	 public void removeUserRoleV2(String userId, String roleName) {
	     repoEntityUserRole.deleteRoleFromUser(UUID.fromString(userId),roleName);
	 }

	 @Override
	 public void createSomeUser() {
			
			EntityOrganization organization = new EntityOrganization();
		    organization.setOrganizationName("Kite Limited");
		    organization.setGstno("27ABCDE1234F1Z5");
		    organization.setCeo("Suraj Prajapati");
		    organization.setSalesPerson("Ravi Sharma");
		    organization.setType(EnumOrganizationType.SYSTEM);
		    organization.setPhoneno("9876543210");
		    organization.setEmail("kite@limited.com");
		    organization.setWebsite("www.kitelimited.com");
		    organization.setStreet("Santacruz East");
		    organization.setCity("Mumbai");
		    organization.setState("Maharashtra");
		    organization.setCountryCode(EnumCountry.INDIA);
		    organization.setPincode("400050");
		    
		    repoEntityOrganization.save(organization);
		    
		    EntityRole admin = new EntityRole();
			admin.setRoleName("MANAGER");
			
			EntityRole user = new EntityRole();
			user.setRoleName("SALES");	
			
			List<EntityRole> lst = List.of(user,admin);
			
			repoEntityRole.saveAll(lst);
	 }

}
