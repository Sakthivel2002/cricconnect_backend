package com.sakthi.cricconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recruiters")
@Data
@Builder // <-- Add this
@NoArgsConstructor
@AllArgsConstructor
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private String location;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
