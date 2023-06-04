package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.repositories.SponsorRepository;
import com.swifticket.web.services.SponsorServices;
import jakarta.transaction.Transactional;
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
        return repository.findAll();
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
    @Transactional(rollbackOn = Exception.class)
    public void save(String name, String image) throws Exception{
        Sponsor sponsor = new Sponsor();
        sponsor.setName(name);
        sponsor.setImage(image);

        repository.save(sponsor);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void update(int id, String name, String image) throws Exception{
        Sponsor sponsor = repository.findById(id).orElse(null);
        assert sponsor != null;

        repository.save(sponsor);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(int id) throws Exception { repository.deleteById(id);}
}
