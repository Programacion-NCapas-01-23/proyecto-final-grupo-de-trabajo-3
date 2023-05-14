package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.User;

public interface UserServices {
	List<User> findAll();
	User findOneById(String id);
	void register(String name, String email, String password, String avatar);
	void update(String id, String name, String avatar);
	void changePassword(String id, String password);
	void toggleStatus(String id);
	
	void assignRole(String id, String role);
	void removeRole(String id, String role);
	
	void assignToEvent(String id, String eventId);
	void removeFromEvent(String id, String eventId);
}
