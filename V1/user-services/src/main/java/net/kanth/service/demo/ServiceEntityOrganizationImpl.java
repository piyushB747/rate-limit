package net.kanth.service.demo;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.kanth.entity.EntityOrganization;
import net.kanth.repo.RepoEntityOrganization;
import net.kanth.service.ServiceEntityOrganization;

@AllArgsConstructor
@Service
public class ServiceEntityOrganizationImpl implements ServiceEntityOrganization{

	private RepoEntityOrganization repoEntityOrganization;
	
	@Override
	public EntityOrganization findByOrganizationName(String organizationName) {
		Optional<EntityOrganization> organization = repoEntityOrganization.findByOrganizationName(organizationName);
		
		return organization.isPresent()?organization.get():null;
	}

}
