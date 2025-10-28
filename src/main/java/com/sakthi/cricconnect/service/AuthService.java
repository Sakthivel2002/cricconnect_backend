package com.sakthi.cricconnect.service;

import com.sakthi.cricconnect.dto.RegisterRequest;
import com.sakthi.cricconnect.model.*;
import com.sakthi.cricconnect.repository.PlayerRepository;
import com.sakthi.cricconnect.repository.RecruiterRepository;
import com.sakthi.cricconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;
    private final RecruiterRepository recruiterRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PlayerRepository playerRepository,
                       RecruiterRepository recruiterRepository,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.recruiterRepository = recruiterRepository;
        this.jwtService = jwtService;
    }

    /**
     * Register a new user and create the corresponding Player or Recruiter record.
     */
    public User register(RegisterRequest request) {
        // Check if email already exists
        Optional<User> existing = userRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        // Save user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        User savedUser = userRepository.save(user);

        // Add role-specific entry
        if (request.getRole() == Role.PLAYER) {
            Player player = Player.builder()
                    .user(savedUser)
                    .name(request.getName())
                    .city(request.getCity())
                    .role(request.getPlayerRole())
                    .wage(request.getWage())
                    .status(PlayerStatus.AVAILABLE)
                    .build();
            playerRepository.save(player);
        } else if (request.getRole() == Role.RECRUITER) {
            Recruiter recruiter = new Recruiter();
            recruiter.setUser(savedUser);
            recruiter.setTeamName(request.getTeamName());
            recruiter.setLocation(request.getLocation());
            recruiterRepository.save(recruiter);
        }

        return savedUser;
    }

    /**
     * Login user and return JWT token.
     */
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email!"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }

        return jwtService.generateToken(user.getEmail(), user.getRole().name());
    }

    /**
     * Get a user by email.
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
