package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id", referencedColumnName = "id",nullable = false)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id", referencedColumnName = "id", nullable = false)
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id", nullable = false)
    private Ticket ticket;

    @Column(name = "req_at", nullable = false)
    private Date reqAt;

    @Column(name = "req_expires_at", nullable = false)
    private Date reqExpiresAt;

    @Column(name = "accept_at", nullable = false)
    private Date acceptAt;

    @Column(name = "accept_expires_at", nullable = false)
    private Date acceptExpiresAt;

    @Column(name = "finished_at", nullable = false)
    private Date finishedAt;
}
