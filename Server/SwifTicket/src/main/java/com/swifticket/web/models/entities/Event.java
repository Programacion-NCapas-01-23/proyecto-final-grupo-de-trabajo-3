package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
