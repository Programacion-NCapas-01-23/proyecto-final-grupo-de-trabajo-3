package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Role;
import com.swifticket.web.repositories.RoleRepository;
import com.swifticket.web.services.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServicesImpl implements RoleServices {
    private final RoleRepository repository;

    @Autowired
    public RoleServicesImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Role> findAll() {return repository.findAll();}

    @Override
    public Role findById(int id) {
        return repository.findById(id).orElse(null);
    }
}
