package com.swifticket.web.services.implementations;

import com.swifticket.web.models.dtos.user.ChangePasswordDTO;
import com.swifticket.web.models.dtos.user.UpdateUserDTO;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.repositories.RolexUserRepository;
import com.swifticket.web.repositories.UserRepository;
import com.swifticket.web.services.UserServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final RolexUserRepository rolexUserRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServicesImpl(UserRepository userRepository, RolexUserRepository rolexUserRepository) {
        this.userRepository = userRepository;
        this.rolexUserRepository = rolexUserRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOneById(String id) {
        UUID userId = UUID.fromString(id);
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findOneByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void register(String name, String email, String password, Avatar avatar, UserState state) throws Exception {
        User user = new User(state, avatar, name, email, passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void update(User user, UpdateUserDTO data, Avatar avatar) throws Exception {
        user.setEmail(data.getEmail());
        user.setName(data.getName());
        user.setAvatar(avatar);

        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean changePassword(User user, ChangePasswordDTO data) throws Exception {
        if (!passwordEncoder.matches(data.getPassword(), user.getPassword()))
            return false;

        user.setPassword(passwordEncoder.encode(data.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void toggleStatus(User user, UserState state) throws Exception {
        user.setState(state);
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void assignRole(User user, Role role) throws Exception {
        RolexUser relation = new RolexUser(role, user);
        rolexUserRepository.save(relation);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeRole(User user, Role role) throws Exception {
        RolexUser relation = rolexUserRepository.findOneByRoleAndUser(role, user);
        if (relation == null)
            throw new Exception("Relation found");

        rolexUserRepository.deleteById(relation.getId());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void assignToEvent(String id, String eventId) throws Exception {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // TODO Logic to assign user to an event
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeFromEvent(String id, String eventId) throws Exception {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // TODO Logic to remove user from an event
        } else {
            throw new Exception("User not found");
        }
    }
}

