package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Avatar;
import com.swifticket.web.repositories.AvatarRepository;
import com.swifticket.web.services.AvatarServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvatarServicesImpl implements AvatarServices {
    private final AvatarRepository repository;

    @Autowired
    public AvatarServicesImpl(AvatarRepository repository) {
        this.repository = repository;
    }

    @Override
    public Avatar findById(int id) {
        return repository.findById(id).orElse(null);
    }
}
