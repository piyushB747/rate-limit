package net.kanth.payload;

import java.util.Set;

import lombok.Data;
@Data
public class PayloadUser {
	
	private String username;

	private String firstName;

	private String lastName;
	
	private String password;
	
	private PayloadAddress address;
	
	private Set<PayloadRole> roles;
	
	private PayloadOrganization organization;
		
	
	
	
}
