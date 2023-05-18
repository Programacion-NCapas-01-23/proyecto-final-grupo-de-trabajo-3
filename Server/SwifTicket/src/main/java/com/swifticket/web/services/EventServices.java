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
	Event findOneById(String id);
	void save(SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception;
	void update(String id, SaveEventDTO eventInfo);
	void changeStatus(String id, String status);
	
	void assignSponsor(String id, String sponsor);
	void removeSponsor(String id, String sponsor);
	
	List<Tier> findEventTiers(String eventId);	
	void createTier(String eventId, Tier tier);
	void updateTier(UUID tierId, Tier tier);
	void deleteTier(UUID tierId);
}
