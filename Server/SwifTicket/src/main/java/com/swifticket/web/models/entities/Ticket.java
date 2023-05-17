package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tier_id", referencedColumnName = "id", nullable = false)
    private Tier tier;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;
}
