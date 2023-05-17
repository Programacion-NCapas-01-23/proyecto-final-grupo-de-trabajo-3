package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.repositories.SponsorRepository;
import com.swifticket.web.services.SponsorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SponsorServicesImpl implements SponsorServices {
    private final SponsorRepository repository;

    @Autowired
    public SponsorServicesImpl(SponsorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Sponsor> findAll() {
        return null;
    }

    @Override
    public void save(String name, String image) {
        Sponsor sponsor = new Sponsor();
        sponsor.setName(name);
        sponsor.setImage(image);

        repository.save(sponsor);
    }

    @Override
    public void update(int id, String name, String image) {
        Sponsor sponsor = repository.findById(id).orElse(null);
        assert sponsor != null;

        repository.save(sponsor);
    }

    @Override
    public void delete(int id) {repository.deleteById(id);}
}
