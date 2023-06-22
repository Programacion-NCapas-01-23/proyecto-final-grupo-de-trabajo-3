package com.swifticket.web.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sponsors")
@ToString(exclude = {"eventxSponsors"})
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "image", length = 100, nullable = false)
    private String image;

    @OneToMany(mappedBy = "sponsor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EventxSponsor> eventxSponsors;

    public Sponsor(String name, String image) {
        this.name = name;
        this.image = image;
    }
}