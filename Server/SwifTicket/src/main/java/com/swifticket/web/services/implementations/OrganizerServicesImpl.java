package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.repositories.OrganizerRepository;
import com.swifticket.web.services.OrganizerServices;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Organizer> findAll(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return repository.findByNameContains(name, pageable);
    }

    @Override
	public Organizer findById(int id) {
		return repository.findById(id).orElse(null);
	}

    @Override
    public Organizer findOneByName(String name) {
        return repository.findOneByName(name);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(String name) throws Exception {
        Organizer organizer = new Organizer();
        organizer.setName(name);

        repository.save(organizer);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void update(int id, String name) throws Exception {
        Organizer organizer = repository.findById(id).orElse(null);
        assert organizer != null;
        
        organizer.setName(name);
        repository.save(organizer);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(int id) throws Exception {repository.deleteById(id);}

}
