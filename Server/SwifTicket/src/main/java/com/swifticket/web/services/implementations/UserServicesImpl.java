package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Avatar;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.models.entities.UserState;
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
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    @Transactional(rollbackOn = Exception.class)
    public void register(String name, String email, String password, Avatar avatar, UserState state) throws Exception {
        User user = new User(state, avatar, name, email, passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void update(String id, String name, Avatar avatar) throws Exception {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setName(name);
            user.setAvatar(avatar);
            userRepository.save(user);
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void changePassword(String id, String password) throws Exception {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setPassword(password);
            userRepository.save(user);
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void toggleStatus(String id, String status) throws Exception {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // TODO Change the status of the user
            //user.setStatus(status);
            userRepository.save(user);
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void assignRole(String id, String role) throws Exception {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // TODO Logic to assign a role to the user
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeRole(String id, String role) throws Exception {
        UUID userId = UUID.fromString(id);
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // TODO Logic to remove a role from the user
        } else {
            throw new Exception("User not found");
        }
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

