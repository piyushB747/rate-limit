package net.kanth.service;

import java.util.List;
import java.util.UUID;

import net.kanth.payload.PayloadUser;

public interface ServiceEntityUser {
	
	PayloadUser saveUser(PayloadUser payload);

	void testToSaveUserRoles(String userId);

	Object deleteById(UUID id);

	PayloadUser updateUserByIdV2(UUID uuid, PayloadUser payload);
	
	List<PayloadUser> findAll();

	PayloadUser findById(UUID uuid);

	PayloadUser findByUsername(String username);

	
	




}
