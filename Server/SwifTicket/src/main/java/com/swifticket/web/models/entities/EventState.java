package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "event_states")
public class EventState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "event_state_name", nullable = false, length = 50)
    private String name;
}
