package com.sakthi.cricconnect.service;

import com.sakthi.cricconnect.model.Booking;
import com.sakthi.cricconnect.model.BookingStatus;
import com.sakthi.cricconnect.model.Player;
import com.sakthi.cricconnect.model.PlayerStatus;
import com.sakthi.cricconnect.repository.BookingRepository;
import com.sakthi.cricconnect.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PlayerRepository playerRepository;

    public BookingService(BookingRepository bookingRepository, PlayerRepository playerRepository){
        this.bookingRepository = bookingRepository;
        this.playerRepository = playerRepository;
    }

    public Booking createBooking(Booking booking){
        Player player = playerRepository.findById(booking.getPlayer().getId())
                .orElseThrow(() -> new RuntimeException("Player not found!"));
        if(player.getStatus() == PlayerStatus.BOOKED){
            throw new RuntimeException("Player is already booked!");
        }
        player.setStatus(PlayerStatus.BOOKED);
        playerRepository.save(player);
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByRecruiter(Long recruiterId){
        return bookingRepository.findByRecruiter_Id(recruiterId);
    }
}
