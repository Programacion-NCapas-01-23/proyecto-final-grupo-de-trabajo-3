package com.swifticket.web.services.implementations;

import com.swifticket.web.models.dtos.event.SaveEventDTO;
import com.swifticket.web.models.dtos.sponsor.SaveSponsorDTO;
import com.swifticket.web.models.dtos.tier.SaveTierDTO;
import com.swifticket.web.models.dtos.tier.UpdateTierDTO;
import com.swifticket.web.models.entities.*;
import com.swifticket.web.repositories.*;
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
    private final EventxSponsorRepository eventxSponsorRepository;



    @Autowired
    public EventServicesImpl(EventRepository eventRepository, EventStateRepository eventStateRepository, TierRepository tierRepository, SponsorRepository sponsorRepository, EventxSponsorRepository eventxSponsorRepository) {
        this.eventRepository = eventRepository;
        this.eventStateRepository = eventStateRepository;
        this.tierRepository = tierRepository;
        this.sponsorRepository = sponsorRepository;
        this.eventxSponsorRepository = eventxSponsorRepository;
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
    public EventxSponsor findByEventAndSponsor(Event event, Sponsor sponsor) {
        return eventxSponsorRepository.findOneByEventAndSponsor(event, sponsor);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void assignSponsor(Event event, Sponsor sponsor) throws Exception {
        EventxSponsor relation = new EventxSponsor(event, sponsor);
        eventxSponsorRepository.save(relation);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removeSponsor(Event event, Sponsor sponsor) throws Exception {
        EventxSponsor relation = eventxSponsorRepository.findOneByEventAndSponsor(event, sponsor);
        if (relation == null)
            throw new Exception("Relation not found");

        eventxSponsorRepository.deleteById(relation.getId());
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

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createTier(SaveTierDTO tierData) throws Exception {
        UUID id = UUID.fromString(tierData.getEventId());
        Event event = eventRepository.findById(id).orElse(null);

        if (event == null) return;

        Tier newTier = new Tier(event, tierData.getName(), tierData.getCapacity(), tierData.getPrice());
        tierRepository.save(newTier);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateTier(String tierId, UpdateTierDTO tierData) throws Exception {
        UUID id = UUID.fromString(tierId);
        Tier tier = tierRepository.findById(id).orElse(null);

        if (tier == null) return;
        if (tier.getTickets().size() > tierData.getCapacity()) return;

        tier.setName(tierData.getName());
        tier.setCapacity(tierData.getCapacity());
        tier.setPrice(tierData.getPrice());

        tierRepository.save(tier);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteTier(String tierId) throws Exception {
        UUID id = UUID.fromString(tierId);
        tierRepository.deleteById(id);
    }
}