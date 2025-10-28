package com.sakthi.cricconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // PLAYER specific fields
    private String name;                  // player name
    private String city;                  // player city
    @Enumerated(EnumType.STRING)
    private PlayerRole playerRole;        // BATTER, BOWLER, etc.
    private Double wage;                  // player wage

    // RECRUITER specific fields
    private String teamName;              // recruiter team name
    private String location;              // recruiter location
}
