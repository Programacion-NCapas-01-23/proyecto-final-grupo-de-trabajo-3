package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.Tier;

public interface EventServices {
	List<Event> findAll();
	Event findOneById(String id);
	void save(SaveEventDTO eventInfo);
	void update(String id, SaveEventDTO eventInfo);
	void changeStatus(String id, String status);
	
	void assignSponsor(String id, String sponsor);
	void removeSponsor(String id, String sponsor);
	
	List<Tier> findEventTiers(String eventId);	
	void createTier(String eventId, Tier tier);
	void updateTier(String tierId, Tier tier);
	void deleteTier(String tierId);
}
