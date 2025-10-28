package com.sakthi.cricconnect.controller;

import com.sakthi.cricconnect.model.*;
import com.sakthi.cricconnect.repository.PlayerRepository;
import com.sakthi.cricconnect.repository.RecruiterRepository;
import com.sakthi.cricconnect.repository.BookingRepository;
import com.sakthi.cricconnect.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final PlayerRepository playerRepository;
    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    public BookingController(BookingRepository bookingRepository,
                             PlayerRepository playerRepository,
                             RecruiterRepository recruiterRepository,
                             UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.playerRepository = playerRepository;
        this.recruiterRepository = recruiterRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Extract logged-in email from token
        System.out.println("[BookingController] Authenticated email = " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));

        Recruiter recruiter = recruiterRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter not found for logged-in user"));

        Player player = playerRepository.findById(booking.getPlayer().getId())
                .orElseThrow(() -> new RuntimeException("Player not found"));

        // Check if player already booked
        if (player.getStatus() == PlayerStatus.BOOKED) {
            return ResponseEntity.badRequest().body("Player already booked!");
        }

        // Update player status
        player.setStatus(PlayerStatus.BOOKED);
        playerRepository.save(player);

        // Prepare booking
        booking.setPlayer(player);
        booking.setRecruiter(recruiter);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setDate(LocalDate.now());

        Booking saved = bookingRepository.save(booking);
        System.out.println("[BookingController] Booking created successfully by recruiter: " + recruiter.getId());

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<Booking>> getMyBookings(Principal principal) {
        String email = principal.getName(); // from JWT
        System.out.println("[BookingController] Authenticated email = " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Recruiter recruiter = recruiterRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("Recruiter not found for logged-in user"));

        List<Booking> bookings = bookingRepository.findByRecruiter_Id(recruiter.getId());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/player")
    public ResponseEntity<List<Booking>> getMyBookingsAsPlayer(Principal principal) {
        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Player> players = playerRepository.findByUser_Id(user.getId());
        if (players.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        List<Booking> bookings = players.stream()
                .flatMap(p -> bookingRepository.findByPlayer_Id(p.getId()).stream())
                .toList();

        return ResponseEntity.ok(bookings);
    }


    // Fetch bookings for a specific player by playerId (admin/recruiter)
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Booking>> getBookingsByPlayerId(@PathVariable Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found with ID: " + playerId));

        List<Booking> bookings = bookingRepository.findByPlayer_Id(player.getId());
        return ResponseEntity.ok(bookings);
    }

}
