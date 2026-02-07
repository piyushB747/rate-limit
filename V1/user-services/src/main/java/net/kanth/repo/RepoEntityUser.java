package net.kanth.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.kanth.entity.EntityUser;
import net.kanth.payload.UserNameDTO;

public interface RepoEntityUser extends JpaRepository<EntityUser, UUID> {

	@Query(name = "EntityUser.findByUserName")
	public Optional<EntityUser> findByUsername(String username);

	@Query(name = "EntityUser.findByRoleName", nativeQuery = true)
	public List<UserNameDTO> findByRoleName(String roleName);
	
	
}
