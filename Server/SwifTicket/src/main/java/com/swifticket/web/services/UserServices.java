package com.swifticket.web.services;

import java.util.List;
import java.util.UUID;

import com.swifticket.web.models.entities.Avatar;
import com.swifticket.web.models.entities.User;

public interface UserServices {
	List<User> findAll();
	User findOneById(String id);
	void register(String name, String email, String password, Avatar avatar) throws Exception;
	void update(String id, String name, Avatar avatar) throws Exception;
	void changePassword(String id, String password) throws Exception;
	void toggleStatus(String id, String status) throws Exception;
	
	void assignRole(String id, String role) throws Exception;
	void removeRole(String id, String role) throws Exception;
	
	void assignToEvent(String id, String eventId) throws Exception;
	void removeFromEvent(String id, String eventId) throws Exception;
}
