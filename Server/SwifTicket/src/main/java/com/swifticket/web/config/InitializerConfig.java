package com.swifticket.web.config;

import com.swifticket.web.utils.SuperAdminInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializerConfig {
    private final SuperAdminInitializer superAdminInitializer;
    @Autowired
    public InitializerConfig(SuperAdminInitializer superAdminInitializer) {
        this.superAdminInitializer = superAdminInitializer;
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return superAdminInitializer;
    }

}
