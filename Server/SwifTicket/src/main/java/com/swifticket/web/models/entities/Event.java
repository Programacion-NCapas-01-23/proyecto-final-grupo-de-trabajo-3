package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "id", nullable = false)
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "id",nullable = false)
    private EventState state;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private double duration;

    @Column(name = "date_time", nullable = false)
    private Date dateTime;

    @Column(length = 100, name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;
}
