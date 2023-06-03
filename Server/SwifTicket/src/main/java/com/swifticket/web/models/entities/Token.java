package com.swifticket.web.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id", nullable = false)
    private Ticket ticket;

    @Column(name = "verified_at", nullable = false)
    private Date verifiedAt;

    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public Token(Ticket ticket, Date expiresAt) {
        this.ticket = ticket;
        this.expiresAt = expiresAt;
    }
}
