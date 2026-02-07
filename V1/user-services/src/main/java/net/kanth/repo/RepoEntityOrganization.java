package net.kanth.repo;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import net.kanth.entity.EntityOrganization;

public interface RepoEntityOrganization extends JpaRepository<EntityOrganization, UUID>{

	Optional<EntityOrganization> findByOrganizationName(String organizationName);
}
