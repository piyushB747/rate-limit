package net.kanth.repo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import net.kanth.entity.EntityOrganization;
import net.kanth.enums.EnumCountry;
import net.kanth.enums.EnumOrganizationType;

@SpringBootTest(classes = net.kanth.UserServicesApplication.class)
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
	    organization.setCountryCode(EnumCountry.INDIA);
	    organization.setPincode("400050");
	    
	    repoEntityOrganization.save(organization);
	}

}
