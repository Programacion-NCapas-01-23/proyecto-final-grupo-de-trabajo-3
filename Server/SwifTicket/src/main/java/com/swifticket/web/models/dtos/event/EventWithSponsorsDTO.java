package com.swifticket.web.models.dtos.event;

import com.swifticket.web.models.dtos.tier.TierDTO;
import com.swifticket.web.models.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
// DTO just to get THE EVENT with SPONSORS data and not to set it
public class EventWithSponsorsDTO {
    private UUID id;
    private Category category;
    private Organizer organizer;
    private EventState state;
    private String title;
    private double duration;
    private Date dateTime;
    private String image;
    private Place place;
    private List<TierDTO> tiers;
    private List<Sponsor> sponsors;
    private boolean isAvailable;

    public EventWithSponsorsDTO(Event event, List<TierDTO> tiers, List<Sponsor> sponsors, boolean isAvailable) {
        this.id = event.getId();
        this.category = event.getCategory();
        this.organizer = event.getOrganizer();
        this.state = event.getState();
        this.title = event.getTitle();
        this.duration = event.getDuration();
        this.dateTime = event.getDateTime();
        this.image = event.getImage();
        this.place = event.getPlace();
        this.tiers = tiers;
        this.sponsors = sponsors;
        this.isAvailable = isAvailable;
    }
}
