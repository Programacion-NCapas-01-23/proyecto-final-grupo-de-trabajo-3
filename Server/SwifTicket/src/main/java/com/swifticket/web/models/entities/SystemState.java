package com.swifticket.web.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "system_state")
public class SystemState {
    @Id
    private int id;

    @Column(name = "state", nullable = false, columnDefinition = "INT DEFAULT 1")
    private int state;
}
