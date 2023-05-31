package com.swifticket.web.services;

import java.util.List;
import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.dtos.event.UpdateEventTierDTO;
import com.swifticket.web.models.dtos.sponsor.SaveSponsorDTO;
import com.swifticket.web.models.dtos.tier.SaveTierDTO;
import com.swifticket.web.models.dtos.tier.UpdateTierDTO;
import com.swifticket.web.models.entities.Category;
import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.models.entities.Place;
import com.swifticket.web.models.entities.Tier;

public interface EventServices {
	List<Event> findAll();
	Event findOneById(String id);
	void save(SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception;
	void update(String id, SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception;
	void changeStatus(String id, String status) throws Exception;

	void assignSponsor(String eventId, SaveSponsorDTO sponsorData) throws Exception;
	void removeSponsor(String eventId, int sponsor) throws Exception;

	List<Tier> findEventTiers(String eventId);
	void createTier(String eventId, SaveTierDTO tierData) throws Exception;
	void updateTier(String tierId, UpdateTierDTO tierData) throws Exception;
	void deleteTier(String tierId) throws Exception;
}
