package com.sakthi.cricconnect.dto;

import com.sakthi.cricconnect.model.Role;
import com.sakthi.cricconnect.model.PlayerRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;

    // PLAYER fields
    private String name;
    private String city;
    private PlayerRole playerRole;
    private Double wage;

    // RECRUITER fields
    private String teamName;
    private String location;
}
