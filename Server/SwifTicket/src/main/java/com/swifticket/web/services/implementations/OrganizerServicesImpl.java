package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.repositories.OrganizerRepository;
import com.swifticket.web.services.OrganizerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerServicesImpl implements OrganizerServices {

    private final OrganizerRepository repository;

    @Autowired
    public OrganizerServicesImpl(OrganizerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Organizer> findAll() {return repository.findAll();}

    @Override
    public void save(String name) {
        Organizer organizer = new Organizer();
        organizer.setName(name);

        repository.save(organizer);
    }

    @Override
    public void update(int id, String name) {
        Organizer organizer = repository.findById(id).orElse(null);
        assert organizer != null;
        repository.save(organizer);
    }

    @Override
    public void delete(int id) {repository.deleteById(id);
    }
}
