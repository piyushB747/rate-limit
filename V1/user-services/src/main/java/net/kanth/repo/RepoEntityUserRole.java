package net.kanth.repo;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import net.kanth.entity.EntityUserRole;
import net.kanth.entity.UserRoleId;

public interface RepoEntityUserRole extends JpaRepository<EntityUserRole, UserRoleId>{

	  @Modifying
	    @Query("""
	        DELETE FROM EntityUserRole ur
	        WHERE ur.user.id = :userId
	          AND ur.role.roleName = :roleName
	    """)
	    int deleteRoleFromUser(UUID userId,String roleName);
	
}
