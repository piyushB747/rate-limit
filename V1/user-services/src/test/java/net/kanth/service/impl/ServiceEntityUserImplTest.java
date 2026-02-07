package net.kanth.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.kanth.entity.EntityRole;
import net.kanth.entity.EntityUser;
import net.kanth.entity.EntityUserRole;
import net.kanth.entity.UserRoleId;
import net.kanth.enums.EnumCountry;
import net.kanth.payload.PayloadAddress;
import net.kanth.payload.PayloadUser;
import net.kanth.repo.RepoEntityRole;
import net.kanth.repo.RepoEntityUser;

@Slf4j
@SpringBootTest(classes = net.kanth.UserServicesApplication.class)
@Transactional
@Rollback(false)
class ServiceEntityUserImplTest {

    @Autowired
    private ServiceEntityUserImpl serviceEntityUserImpl;

    @Autowired
    private RepoEntityRole repoEntityRole;
    
    @Autowired
    private RepoEntityUser repoEntityUser;
    
    @Test
    void testToSaveUserRoles() {
    	
		EntityUser userObj = repoEntityUser.findById(UUID.fromString("a753f5a3-2e83-49a6-849f-e2030070354c")).orElseThrow();

    	EntityRole adminRole = repoEntityRole.findById(UUID.fromString("ab467e0f-ccbd-4bff-b02f-3b970ac91722")).orElseThrow();

		log.info("adminRole Details {} ", adminRole);

		UserRoleId idKey = new UserRoleId(userObj.getId(), adminRole.getId());
		EntityUserRole userRoleObj = new EntityUserRole();
		userRoleObj.setRole(adminRole);
		userRoleObj.setUser(userObj);
		userRoleObj.setUserRole(idKey);

		/**************** SECOND PART *******************/
		EntityRole userRole = repoEntityRole.findById(UUID.fromString("b57b6f98-bd65-4a82-be77-5c5786a464ee")).orElseThrow();
		UserRoleId idKeyUser = new UserRoleId(userObj.getId(), userRole.getId());
		EntityUserRole userUserRole = new EntityUserRole();
		userUserRole.setUser(userObj);
		userUserRole.setRole(userRole);
		userUserRole.setUserRole(idKeyUser);
		userObj.getRoles().addAll(List.of(userUserRole, userRoleObj));
		adminRole.getUserRoles().addAll(List.of(userUserRole, userRoleObj));
		
		log.info("UserObject Details {} ", userObj);
		try {
			repoEntityUser.save(userObj);
		}catch(Exception ex) { ex.printStackTrace(); }
		
    	
   
    }
    
    
    @Test
    void testToSaveUsers() throws InterruptedException, ExecutionException {
        PayloadUser p1 = new PayloadUser();
        p1.setFirstName("Sura");
        p1.setLastName("Shinde");
        p1.setUsername("amita10");
        p1.setPassword("abcd1234");

        
        PayloadAddress payloadAddress = new PayloadAddress();
        payloadAddress.setPhoneNo("9320885533");
        payloadAddress.setEmail("amitashinde@gmail.com");
        payloadAddress.setStreet("Bellapur East");
        payloadAddress.setCity("Navi Mumbai");
        payloadAddress.setState("Maharashtra");
        payloadAddress.setCountry(EnumCountry.INDIA);
        
        p1.setAddress(payloadAddress);
        
        serviceEntityUserImpl.saveUser(p1);
        
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        
        
        Callable<PayloadUser> task = () -> serviceEntityUserImpl.saveUser(p1);
        
        Future<PayloadUser> future  =   executorService.submit(task);
        
        log.info(" Life",future.get());
    }
}
