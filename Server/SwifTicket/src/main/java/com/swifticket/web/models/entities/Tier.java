package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "tiers")
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "price", nullable = false)
    private int price;
}

