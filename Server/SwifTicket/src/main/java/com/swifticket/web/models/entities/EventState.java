package com.swifticket.web.models.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "event_states")
public class EventState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Event> events;
}
