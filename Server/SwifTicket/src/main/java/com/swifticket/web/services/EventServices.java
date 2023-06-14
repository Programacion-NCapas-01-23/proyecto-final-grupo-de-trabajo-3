package com.swifticket.web.services;

import java.util.List;
import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.dtos.tier.SaveTierDTO;
import com.swifticket.web.models.dtos.tier.UpdateTierDTO;
import com.swifticket.web.models.entities.*;
import org.springframework.data.domain.Page;

public interface EventServices {
	List<Event> findAll();
	Page<Event> findAll(String title, int page, int size);
	Event findById(String id);
	void save(SaveEventDTO eventInfo, Category category, Organizer organizer, Place place, EventState state) throws Exception;
	void update(String id, SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception;
	void changeStatus(Event event, EventState state) throws Exception;
	EventxSponsor findByEventAndSponsor(Event event, Sponsor sponsor);
	void assignSponsor(Event event, Sponsor sponsor) throws Exception;
	void removeSponsor(Event event, Sponsor sponsor) throws Exception;

	List<Tier> findEventTiers(String eventId);
	void createTier(SaveTierDTO tierData) throws Exception;
	void updateTier(String tierId, UpdateTierDTO tierData) throws Exception;
	void deleteTier(String tierId) throws Exception;
}
