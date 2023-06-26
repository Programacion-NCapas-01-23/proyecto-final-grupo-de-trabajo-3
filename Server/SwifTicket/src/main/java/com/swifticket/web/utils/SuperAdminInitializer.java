package com.swifticket.web.utils;

import com.swifticket.web.models.entities.Role;
import com.swifticket.web.models.entities.User;
import com.swifticket.web.models.entities.UserState;
import com.swifticket.web.repositories.AvatarRepository;
import com.swifticket.web.repositories.UserRepository;
import com.swifticket.web.repositories.UserStateRepository;
import com.swifticket.web.services.AuthServices;
import com.swifticket.web.services.RoleServices;
import com.swifticket.web.services.UserServices;
import com.swifticket.web.services.UserStateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    private final UserStateServices userStateServices;

    private final AuthServices authService;
    private final RoleServices roleServices;
    private final AvatarRepository avatarRepository;
    private final UserServices userServices;
    private final static String SUPER_ADMIN_EMAIL = "dsolismarroquin@gmail.com";
    private final static String SUPER_ADMIN_PASSWORD = "sysadmin!2023";
    private final static String SUPER_ADMIN_NAME = "Super Admin";
    private final static int SUPER_ADMIN_ROLE_ID = 1;
    private final static int SUPER_ADMIN_STATE_ID = 1;
    private final static int SUPER_ADMIN_AVATAR_ID = 1;

    @Autowired
    public SuperAdminInitializer(UserRepository userRepository,
                                 UserStateServices userStateServices,
                                 AuthServices authService,
                                 RoleServices roleServices,
                                 AvatarRepository avatarRepository,
                                 UserServices userServices) {
        this.userRepository = userRepository;
        this.userStateServices = userStateServices;
        this.authService = authService;
        this.roleServices = roleServices;
        this.avatarRepository = avatarRepository;
        this.userServices = userServices;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Verify if super admin exists in the database and create one if not
        if (!userRepository.existsByEmail(SUPER_ADMIN_EMAIL)) {
            // Get super admin role and state from database
            Role role = roleServices.findById(SUPER_ADMIN_ROLE_ID);
            UserState userState = userStateServices.findById(SUPER_ADMIN_STATE_ID);

            // Create super admin
            User superAdmin = new User();
            superAdmin.setEmail(SUPER_ADMIN_EMAIL);
            superAdmin.setPassword(authService.hashPassword(SUPER_ADMIN_PASSWORD));
            superAdmin.setAvatar(avatarRepository.findById(SUPER_ADMIN_AVATAR_ID));
            superAdmin.setName(SUPER_ADMIN_NAME);
            superAdmin.setState(userState);

            // Save super admin and assign role
            userRepository.save(superAdmin);
            userServices.assignRole(superAdmin, role);
            userRepository.save(superAdmin);

        }
    }
}

