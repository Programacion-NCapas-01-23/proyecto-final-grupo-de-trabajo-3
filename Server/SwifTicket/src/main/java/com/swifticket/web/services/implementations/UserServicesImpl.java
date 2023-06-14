package com.swifticket.web.services.implementations;

import com.swifticket.web.models.dtos.user.ChangePasswordDTO;
import com.swifticket.web.models.dtos.user.UpdateUserDTO;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.repositories.AuthTokenRepository;
import com.swifticket.web.repositories.EventxValidatorRepository;
import com.swifticket.web.repositories.RolexUserRepository;
import com.swifticket.web.repositories.UserRepository;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.utils.JWTTools;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final RolexUserRepository rolexUserRepository;
    private final EventxValidatorRepository eventxValidatorRepository;
    private final JWTTools jwtTools;
    private final AuthTokenRepository authTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServicesImpl(UserRepository userRepository, RolexUserRepository rolexUserRepository, EventxValidatorRepository eventxValidatorRepository, JWTTools jwtTools, AuthTokenRepository authTokenRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolexUserRepository = rolexUserRepository;
        this.eventxValidatorRepository = eventxValidatorRepository;
        this.jwtTools = jwtTools;
        this.authTokenRepository = authTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        return userRepository.findAll(pageable);
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
    public User findOneByIdOrEmail(UUID id, String email) {
        return userRepository.findByIdOrEmail(id, email);
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
    public RolexUser findByRoleAndUser(User user, Role role) {
        return rolexUserRepository.findOneByRoleAndUser(role, user);
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
            throw new Exception("Relation not found");

        rolexUserRepository.deleteById(relation.getId());
    }

    @Override
    public EventxValidator findByEventAndUser(Event event, User user) {
        return eventxValidatorRepository.findOneByEventAndUser(event, user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void assignToEvent(User user, Event event) throws Exception {
        EventxValidator relation = new EventxValidator(event, user);
        eventxValidatorRepository.save(relation);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeFromEvent(User user, Event event) throws Exception {
        EventxValidator relation = eventxValidatorRepository.findOneByEventAndUser(event, user);
        if (relation == null)
            throw new Exception("Relation not found");

        eventxValidatorRepository.deleteById(relation.getId());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AuthToken registerToken(User user) throws Exception {
        cleanTokens(user);

        String tokenString = jwtTools.generateToken(user);
        AuthToken authToken = new AuthToken(tokenString, user);

        authTokenRepository.save(authToken);

        return authToken;
    }

    @Override
    public Boolean isTokenValid(User user, String token) {
        try {
            cleanTokens(user);
            List<AuthToken> authTokens = authTokenRepository.findByUserAndActive(user, true);

            authTokens.stream()
                    .filter(tk -> tk.getContent().equals(token))
                    .findAny()
                    .orElseThrow(Exception::new);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cleanTokens(User user) throws Exception {
        List<AuthToken> authTokens = authTokenRepository.findByUserAndActive(user, true);

        authTokens.forEach(token -> {
            if(!jwtTools.verifyToken(token.getContent())) {
                token.setActive(false);
                authTokenRepository.save(token);
            }
        });
    }

    @Override
    public User findUserAuthenticated() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findOneByEmail(username);
    }
}

