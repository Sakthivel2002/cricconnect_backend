package com.sakthi.cricconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "players")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlayerRole role;

    private String city;
    private Double wage;

    @Enumerated(EnumType.STRING)
    private PlayerStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // link to User entity
}
