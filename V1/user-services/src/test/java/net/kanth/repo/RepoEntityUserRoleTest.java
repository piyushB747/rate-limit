package net.kanth.repo;


import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(classes = net.kanth.UserServicesApplication.class)
@Transactional
@Rollback(false)
class RepoEntityUserRoleTest {
	@Autowired
	private RepoEntityUserRole repoEntityUserRole;

	@Test
	void testToDeleteUserRole() {
		int id = repoEntityUserRole.deleteRoleFromUser(UUID.fromString("5a208da3-46a4-4992-a394-2e781b7ee6b1"), "USER");
		
		System.out.println(id+" RISE ABOVE HATE");
	}

}
