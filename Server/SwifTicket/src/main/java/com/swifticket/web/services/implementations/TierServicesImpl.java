package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.repositories.TierRepository;
import com.swifticket.web.services.TierServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TierServicesImpl implements TierServices {
    private final TierRepository tierRepository;
    @Autowired
    public TierServicesImpl(TierRepository tierRepository) {
        this.tierRepository = tierRepository;
    }

    @Override
    public Tier findById(String id) {
        UUID tierId = UUID.fromString(id);
        return tierRepository.findById(tierId).orElse(null);
    }

    @Override
    public List<Tier> findAll() {
        return tierRepository.findAll();
    }
}
