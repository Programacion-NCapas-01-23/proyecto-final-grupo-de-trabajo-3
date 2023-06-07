package com.swifticket.web.services;

import java.util.List;
import java.util.UUID;

import com.swifticket.web.models.dtos.user.ChangePasswordDTO;
import com.swifticket.web.models.dtos.user.UpdateUserDTO;
import com.swifticket.web.models.entities.*;

public interface UserServices {
	List<User> findAll();
	User findOneById(String id);
	User findOneByEmail(String email);
	User findOneByIdOrEmail(UUID id, String email);
	void register(String name, String email, String password, Avatar avatar, UserState state) throws Exception;
	void update(User user, UpdateUserDTO data, Avatar avatar) throws Exception;
	Boolean changePassword(User user, ChangePasswordDTO data) throws Exception;
	void toggleStatus(User user, UserState state) throws Exception;

	RolexUser findByRoleAndUser(User user, Role role);
	void assignRole(User user, Role role) throws Exception;
	void removeRole(User user, Role role) throws Exception;

	EventxValidator findByEventAndUser(Event event, User user);
	void assignToEvent(User user, Event event) throws Exception;
	void removeFromEvent(User user, Event event) throws Exception;
}
