package com.swifticket.web.models.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.Data;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tiers")
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Event event;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "price", nullable = false)
    private int price;

    @OneToMany(mappedBy = "tier", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Ticket> tickets;

	public Tier(Event event, String name, int capacity, int price) {
		super();
		this.event = event;
		this.name = name;
		this.capacity = capacity;
		this.price = price;
	}
    
}

