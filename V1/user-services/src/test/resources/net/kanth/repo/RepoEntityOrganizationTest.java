package net.kanth.repo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.kite.entity.EntityOrganization;
import net.kite.enums.EnumCountryCode;
import net.kite.enums.EnumOrganizationType;
import net.kite.repo.RepoEntityOrganization;


@SpringBootTest
class RepoEntityOrganizationTest {

	@Autowired
	private RepoEntityOrganization repoEntityOrganization;
	
	@Test
	void testToSaveOrganization() {
		
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
	    organization.setCountryCode(EnumCountryCode.IN);
	    organization.setPincode("400050");
	    
	    repoEntityOrganization.save(organization);
	}

}
