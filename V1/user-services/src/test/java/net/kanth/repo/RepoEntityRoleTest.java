package net.kanth.repo;


import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.kanth.entity.EntityRole;

@SpringBootTest(classes = net.kanth.UserServicesApplication.class)
class RepoEntityRoleTest {
	
	@Autowired
	private RepoEntityRole repoEntityRole;
	
	@Test
	void testToSaveRole() {
		
		EntityRole admin = new EntityRole();
		admin.setRoleName("MANAGER");
		
		EntityRole user = new EntityRole();
		user.setRoleName("SALES");	
		
		List<EntityRole> lst = List.of(user,admin);
		
		repoEntityRole.saveAll(lst);
		
		
		//fail("Not yet implemented");
		
	}

}
