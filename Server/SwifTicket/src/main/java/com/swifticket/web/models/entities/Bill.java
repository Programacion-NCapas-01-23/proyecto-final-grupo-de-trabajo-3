package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "ticket_id", nullable = false)
    private UUID ticketId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "total")
    private int total;

    public Bill(UUID ticketId, UUID userId, int total) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.total = total;
    }
}
