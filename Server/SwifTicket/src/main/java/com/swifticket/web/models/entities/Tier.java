package com.swifticket.web.models.entities;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tiers")
public class Tier {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private Event event;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "price", nullable = false)
    private int price;

	public Tier(Event event, String name, int capacity, int price) {
		super();
		this.event = event;
		this.name = name;
		this.capacity = capacity;
		this.price = price;
	}
    
}

