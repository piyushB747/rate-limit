package net.kanth.repo;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.kanth.entity.EntityRole;
import net.kanth.payload.PayloadRole;

import java.util.List;
import java.util.Optional;

public interface RepoEntityRole extends JpaRepository<EntityRole, UUID> {

	@Query("select r.roleName as roleName from EntityRole r")
	List<PayloadRole> findAllRolesOnly();
	
	@Query("SELECT r FROM EntityRole r Where r.roleName =:roleName")
	Optional<EntityRole> findByRoleName(String roleName);
	
}
