package com.swifticket.web.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@ToString(exclude = {"rolexUsers"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RolexUser> rolexUsers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    private UserState state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public User(UserState state, Avatar avatar, String name, String email, String password) {
        this.state = state;
        this.avatar = avatar;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
