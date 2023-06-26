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
        if (!userRepository.existsByEmail("superadmin@example.com")) {
            Role role = roleServices.findById(1);
            UserState userState = userStateServices.findById(1);

            User superAdmin = new User();
            superAdmin.setEmail("dsolismarroquin@gmail.com");
            superAdmin.setPassword(authService.hashPassword("sysadmin!2023"));
            superAdmin.setAvatar(avatarRepository.findById(1));
            superAdmin.setName("Super Admin");
            superAdmin.setState(userState);

            userRepository.save(superAdmin);
            userServices.assignRole(superAdmin, role);
            userRepository.save(superAdmin);

        }
    }
}

