package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event_x_sponsor")
public class EventxSponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "sponsor_id", referencedColumnName = "id")
    private Sponsor sponsor;

    public EventxSponsor(Event event, Sponsor sponsor) {
        this.event = event;
        this.sponsor = sponsor;
    }
}
