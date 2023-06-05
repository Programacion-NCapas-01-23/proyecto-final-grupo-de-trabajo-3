package com.swifticket.web.services;

import java.util.List;
import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.dtos.tier.SaveTierDTO;
import com.swifticket.web.models.dtos.tier.UpdateTierDTO;
import com.swifticket.web.models.entities.*;

public interface EventServices {
	List<Event> findAll();
	Event findById(String id);
	void save(SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception;
	void update(String id, SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception;
	void changeStatus(String id, String status) throws Exception;
	EventxSponsor findByEventAndSponsor(Event event, Sponsor sponsor);
	void assignSponsor(Event event, Sponsor sponsor) throws Exception;
	void removeSponsor(Event event, Sponsor sponsor) throws Exception;

	List<Tier> findEventTiers(String eventId);
	void createTier(SaveTierDTO tierData) throws Exception;
	void updateTier(String tierId, UpdateTierDTO tierData) throws Exception;
	void deleteTier(String tierId) throws Exception;
}
