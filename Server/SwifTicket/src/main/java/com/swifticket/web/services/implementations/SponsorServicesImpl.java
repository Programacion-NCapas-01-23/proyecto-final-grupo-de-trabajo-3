package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.repositories.SponsorRepository;
import com.swifticket.web.services.SponsorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SponsorServicesImpl implements SponsorServices {
    private final SponsorRepository repository;

    @Autowired
    public SponsorServicesImpl(SponsorRepository repository, Environment environment) {
        this.repository = repository;
    }

    @Override
    public List<Sponsor> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Sponsor> findAll(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return repository.findByNameContains(name, pageable);
    }

    @Override
    public Sponsor findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Sponsor findByName(String name) {
        return repository.findOneByName(name);
    }

    @Override
    public Sponsor findOneByNameAndImage(String name, String image) {
        return repository.findOneByNameAndImage(name, image);
    }

    @Override
    public void save(String name, String image) {
        Sponsor sponsor = new Sponsor(name, image);
        repository.save(sponsor);
    }

    @Override
    public void update(int id, String name, String image) {
        Sponsor sponsor = repository.findById(id).orElse(null);
        assert sponsor != null;
        sponsor.setName(name);
        sponsor.setImage(image);
        repository.save(sponsor);
    }

    @Override
    public void delete(int id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete sponsor: " + e.getMessage());
        }
    }
}
