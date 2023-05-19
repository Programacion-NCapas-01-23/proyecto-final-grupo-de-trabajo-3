package com.swifticket.web.services.implementations;

import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.entities.Category;
import com.swifticket.web.models.entities.Event;
import com.swifticket.web.models.entities.EventState;
import com.swifticket.web.models.entities.Organizer;
import com.swifticket.web.models.entities.Place;
import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.models.entities.Tier;
import com.swifticket.web.repositories.EventRepository;
import com.swifticket.web.repositories.EventStateRepository;
import com.swifticket.web.repositories.SponsorRepository;
import com.swifticket.web.repositories.TierRepository;
import com.swifticket.web.services.EventServices;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventServicesImpl implements EventServices {
    private final EventRepository eventRepository;
    private final EventStateRepository eventStateRepository;
    private final TierRepository tierRepository;
    private final SponsorRepository sponsorRepository;
    
    

    @Autowired
    public EventServicesImpl(EventRepository eventRepository, EventStateRepository eventStateRepository, TierRepository tierRepository, SponsorRepository sponsorRepository) {
        this.eventRepository = eventRepository;
        this.eventStateRepository = eventStateRepository;
        this.tierRepository = tierRepository;
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public List<Event> findAll() {return eventRepository.findAll();}

    @Override
    public Event findOneById(UUID id) {return eventRepository.findById(id).orElse(null);}


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception {
    	Event event = new Event(
        		category,
        		organizer,
        		eventInfo.getTitle(),
        		Double.parseDouble(eventInfo.getDuration()),
        		// TODO: must define a date format for the app
        		new SimpleDateFormat("dd/MM/yyyy").parse(eventInfo.getDateTime()),
        		eventInfo.getImage(),
        		place
        		);
        

        eventRepository.save(event);
    }

    @Override
    public void update(UUID id, SaveEventDTO eventInfo) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            // TODO: Update the event data with the new data from eventInfo
            // ...

            eventRepository.save(event);
        }
    }

    @Override
    public void changeStatus(UUID id, String status) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            EventState eventState = eventStateRepository.findByStatus(status);
            // Set the new status to the event
            if (eventState != null) {
                event.setState(eventState);
                eventRepository.save(event);
            }
        }
    }


    @Override
    public void assignSponsor(UUID id, String sponsor) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            // TODO: Assign a sponsor to the event (add it to the list)
            // ...

            eventRepository.save(event);
        }
    }

    @Override
    public void removeSponsor(UUID id, String sponsor) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            // Get the sponsor from the event and remove it
            Optional<Sponsor> sponsorToRemove = sponsorRepository.findByIdAndEvent(sponsor, event);
            if (sponsorToRemove.isPresent()) {
                // TODO - CHECK: I think that the event need the information of the sponsors and not the sponsor the information of the event.
                //event.getSponsors().remove(sponsorToRemove.get());
                sponsorRepository.delete(sponsorToRemove.get());
            }
            eventRepository.save(event);
        }
    }



    @Override
    public List<Tier> findEventTiers(UUID eventId) {
        return eventRepository.findByEventId(eventId);
    }

    // TODO - CHECK: I think that the event need the information of the tiers and not the tier the information of the event.
    @Override
    public void createTier(UUID eventId, Tier tier) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            tier.setEvent(event);
            eventRepository.save(event);
        }
    }


    @Override
    public void updateTier(UUID tierId, Tier tier) {
        Tier existingTier = tierRepository.findById(tierId).orElse(null);
        if (existingTier != null) {
            existingTier.setName(tier.getName());
            existingTier.setCapacity(tier.getCapacity());
            existingTier.setPrice(tier.getPrice());

            // TODO: CHECK - Update the rest of the fields as needed
            tierRepository.save(existingTier);
        }
    }

    @Override
    public void deleteTier(UUID tierId) {tierRepository.deleteById(tierId);}
}
