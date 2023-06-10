package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "req_at")
    @CreationTimestamp
    private Date reqAt;

    @Column(name = "req_expires_at", nullable = false)
    private Date reqExpiresAt;

    @Column(name = "accept_at")
    private Date acceptAt;

    @Column(name = "accept_expires_at")
    private Date acceptExpiresAt;

    @Column(name = "finished_at")
    private Date finishedAt;

    public Transaction(User toUser, Date reqExpiresAt) {
        this.toUser = toUser;
        this.reqExpiresAt = reqExpiresAt;
    }
}
