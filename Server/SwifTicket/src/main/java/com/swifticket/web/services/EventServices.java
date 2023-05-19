package com.swifticket.web.services;

import java.util.List;
import java.util.UUID;

import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.entities.Category;
import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.models.entities.Place;
import com.swifticket.web.models.entities.Tier;

public interface EventServices {
	List<Event> findAll();
	Event findOneById(UUID id);
	void save(SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception;
	void update(UUID id, SaveEventDTO eventInfo);
	void changeStatus(UUID id, String status);
	
	void assignSponsor(UUID id, String sponsor);
	void removeSponsor(UUID id, String sponsor);
	
	List<Tier> findEventTiers(UUID eventId);
	void createTier(UUID eventId, Tier tier);
	void updateTier(UUID tierId, Tier tier);
	void deleteTier(UUID tierId);
}
