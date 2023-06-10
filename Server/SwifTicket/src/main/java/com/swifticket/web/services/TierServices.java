package com.swifticket.web.services;

import com.swifticket.web.models.entities.Tier;

import java.util.List;

public interface TierServices {
    Tier findById(String id);
    List<Tier> findAll();
}
