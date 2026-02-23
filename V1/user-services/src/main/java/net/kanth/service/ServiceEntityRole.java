package net.kanth.service;

import java.util.List;
import java.util.UUID;

import net.kanth.entity.EntityRole;
import net.kanth.payload.PayloadRole;

public interface ServiceEntityRole {
	
	List<PayloadRole> findAllRoles();
	
	List<EntityRole> findAll();

	EntityRole findById(UUID id);

	Object findUsersByRoleName(String roleName);

	void assignUserRole(String userId, String roleName);

	void removeUserRole(String userId, String roleName);

	void removeUserRoleV2(String userId, String roleName);

	void createSomeUser();


}
