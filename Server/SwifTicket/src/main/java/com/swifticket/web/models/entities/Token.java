package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@Entity
    @Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id", nullable = false)
    private Ticket ticket;

    @Column(name = "verified_at", nullable = false)
    private Date verifiedAt;

    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

}
