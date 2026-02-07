package net.kanth.service;

import net.kanth.entity.EntityOrganization;

public interface ServiceEntityOrganization {

	EntityOrganization findByOrganizationName(String organizationName);
	
}
