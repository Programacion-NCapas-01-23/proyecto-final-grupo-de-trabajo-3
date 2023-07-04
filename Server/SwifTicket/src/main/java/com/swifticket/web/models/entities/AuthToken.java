package com.swifticket.web.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "auth_tokens")
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code")
    private UUID code;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp", insertable = false, updatable = false)
    private Date timestamp;

    @Column(name = "active", insertable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_code")
    @JsonIgnore
    private User user;

    public AuthToken(String content, User user) {
        super();
        this.content = content;
        this.user = user;
    }
}
