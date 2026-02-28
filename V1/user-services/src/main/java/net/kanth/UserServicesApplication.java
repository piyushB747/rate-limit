package net.kanth;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import net.kanth.entity.EntityOrganization;
import net.kanth.entity.EntityRole;
import net.kanth.entity.EntityUser;
import net.kanth.entity.EntityUserRole;
import net.kanth.entity.UserRoleId;
import net.kanth.enums.EnumCountry;
import net.kanth.enums.EnumOrganizationType;
import net.kanth.payload.PayloadAddress;
import net.kanth.payload.PayloadOrganization;
import net.kanth.payload.PayloadUser;
import net.kanth.repo.RepoEntityOrganization;
import net.kanth.repo.RepoEntityRole;
import net.kanth.repo.RepoEntityUser;
import net.kanth.service.impl.ServiceEntityUserImpl;

@Slf4j
@SpringBootApplication(exclude = { EurekaClientAutoConfiguration.class, EurekaDiscoveryClientConfiguration.class })
public class UserServicesApplication implements CommandLineRunner {

	@Autowired
	private RepoEntityRole repoEntityRole;

	@Autowired
	private RepoEntityOrganization repoEntityOrganization;

	@Autowired
	private ServiceEntityUserImpl serviceEntityUserImpl;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RepoEntityUser repoEntityUser;

	public static void main(String[] args) {
		SpringApplication.run(UserServicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {

			if (!repoEntityRole.existsByRoleName("ADMIN")) {
				EntityRole admin = new EntityRole();
				admin.setRoleName("ADMIN");

				EntityRole user = new EntityRole();
				user.setRoleName("USER");

				List<EntityRole> lst = List.of(user, admin);

				repoEntityRole.saveAll(lst);

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

				PayloadUser p1 = new PayloadUser();
				p1.setFirstName("Prem");
				p1.setLastName("Thakur");
				p1.setUsername("prem10");
				p1.setPassword(encoder.encode("abcd123"));

				PayloadOrganization organization2 = new PayloadOrganization();
				organization2.setOrganizationName("Kite Limited");

				p1.setOrganization(organization2);

				PayloadAddress payloadAddress = new PayloadAddress();
				payloadAddress.setPhoneNo("9320885533");
				payloadAddress.setEmail("amitashinde@gmail.com");
				payloadAddress.setStreet("Bellapur East");
				payloadAddress.setCity("Navi Mumbai");
				payloadAddress.setState("Maharashtra");
				payloadAddress.setCountry(EnumCountry.INDIA);

				p1.setAddress(payloadAddress);

				serviceEntityUserImpl.saveUser(p1);

				ExecutorService executorService = Executors.newFixedThreadPool(2);

				Callable<PayloadUser> task = () -> serviceEntityUserImpl.saveUser(p1);

				@SuppressWarnings("unused")
				Future<PayloadUser> future = executorService.submit(task);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void testToSaveUserRoles() {

		EntityUser userObj = repoEntityUser.findByUsername("prem10").orElseThrow();

		// EntityRole adminRole =
		// repoEntityRole.findById(UUID.fromString("ab467e0f-ccbd-4bff-b02f-3b970ac91722")).orElseThrow();
		EntityRole adminRole = repoEntityRole.findByRoleName("ADMIN").orElseThrow();

		log.info("adminRole Details {} ", adminRole);

		UserRoleId idKey = new UserRoleId(userObj.getId(), adminRole.getId());
		EntityUserRole userRoleObj = new EntityUserRole();
		userRoleObj.setRole(adminRole);
		userRoleObj.setUser(userObj);
		userRoleObj.setUserRole(idKey);


		EntityRole userRole = repoEntityRole.findByRoleName("USER").orElseThrow();
		UserRoleId idKeyUser = new UserRoleId(userObj.getId(), userRole.getId());
		EntityUserRole userUserRole = new EntityUserRole();
		userUserRole.setUser(userObj);
		userUserRole.setRole(userRole);
		userUserRole.setUserRole(idKeyUser);
		userObj.getRoles().addAll(List.of(userUserRole, userRoleObj));
		adminRole.getUserRoles().addAll(List.of(userUserRole, userRoleObj));

		log.info("UserObject Details {} ", userObj);
		try {
			repoEntityUser.save(userObj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
