package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "address", length = 100, nullable = false)
    private String address;
}
