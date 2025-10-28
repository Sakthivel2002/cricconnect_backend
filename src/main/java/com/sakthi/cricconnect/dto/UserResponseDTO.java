package com.sakthi.cricconnect.dto;

import com.sakthi.cricconnect.model.Role;
import com.sakthi.cricconnect.model.PlayerRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private long id;
    private String email;
    private Role role;

    // Player fields
    private String name;
    private String city;
    private PlayerRole playerRole;
    private Double wage;

    // Recruiter fields
    private String teamName;
    private String location;
}
