package com.swifticket.web.services.implementations;

import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.dtos.sponsor.SaveSponsorDTO;
import com.swifticket.web.models.dtos.tier.SaveTierDTO;
import com.swifticket.web.models.dtos.tier.UpdateTierDTO;
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

import java.text.SimpleDateFormat;
import java.util.List;
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
    public List<Event> findAll() { return eventRepository.findAll(); }

    @Override
    public Event findById(String id) {
        try {
            UUID eventId = UUID.fromString(id);
            return eventRepository.findById(eventId).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }


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
    @Transactional(rollbackOn = Exception.class)
    public void update(String id, SaveEventDTO eventInfo, Category category, Organizer organizer, Place place) throws Exception {
        UUID eventId = UUID.fromString(id);
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event != null) {
            event.setCategory(category);
            event.setOrganizer(organizer);
            event.setTitle(eventInfo.getTitle());
            event.setDuration(Double.parseDouble(eventInfo.getDuration()));
            event.setDateTime(new SimpleDateFormat("dd/MM/yyyy").parse(eventInfo.getDateTime()));
            event.setImage(eventInfo.getImage());
            event.setPlace(place);

            eventRepository.save(event);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void changeStatus(String id, String status) throws Exception {
        UUID eventId = UUID.fromString(id);
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event != null) {
            EventState eventState = eventStateRepository.findByState(status);
            // Set the new status to the event
            if (eventState != null) {
                event.setState(eventState);
                eventRepository.save(event);
            }
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void assignSponsor(String eventId, SaveSponsorDTO sponsorData) throws Exception {
        UUID id = UUID.fromString(eventId);
        Event event = eventRepository.findById(id).orElse(null);
        //event.getSponsors().add(new Sponsor(event, sponsorData.getName(), sponsorData.getImage());
        eventRepository.save(event);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeSponsor(String id, int sponsor) throws Exception {
        // TODO: fix this...
    	/*
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
        */
    }



    @Override
    public List<Tier> findEventTiers(String eventId)  {
        try {
            UUID id = UUID.fromString(eventId);
            Event event = eventRepository.findById(id).orElse(null);

            assert event != null;
            return event.getTiers();
        } catch (Exception e) {
            return null;
        }
    }

    // TODO - CHECK: I think that the event need the information of the tiers and not the tier the information of the event.
    // DONE - CHECK: Like this?
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createTier(String eventId, SaveTierDTO tierData) throws Exception {
        UUID id = UUID.fromString(eventId);
        Event event = eventRepository.findById(id).orElse(null);

        if (event != null) {
            Tier newTier = new Tier(event, tierData.getName(), tierData.getCapacity(), tierData.getPrice());
            List<Tier> currentTiers = event.getTiers();
            currentTiers.add(newTier);

            event.setTiers(currentTiers);
            eventRepository.save(event);
        }
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateTier(String tierId, UpdateTierDTO tierData) throws Exception {
        UUID id = UUID.fromString(tierId);
        Tier existingTier = tierRepository.findById(id).orElse(null);

        if (existingTier != null) {
            existingTier.setName(tierData.getName());
            existingTier.setCapacity(tierData.getCapacity());
            existingTier.setPrice(tierData.getPrice());

            // TODO: Validate that the capacity is no lower than the number of tickets sold
            tierRepository.save(existingTier);
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteTier(String tierId) throws Exception {
        UUID id = UUID.fromString(tierId);
        tierRepository.deleteById(id);
    }
}