package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.UserState;
import com.swifticket.web.repositories.UserStateRepository;
import com.swifticket.web.services.UserStateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStateServiceImpl implements UserStateServices {

    @Autowired
    private UserStateRepository userStateRepository;

    @Override
    public UserState findById(int id) {
        return userStateRepository.findById(id).orElse(null);
    }
}
