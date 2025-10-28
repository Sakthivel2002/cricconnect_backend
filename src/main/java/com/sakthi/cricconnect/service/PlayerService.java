package com.sakthi.cricconnect.service;

import com.sakthi.cricconnect.model.Player;
import com.sakthi.cricconnect.model.PlayerRole;
import com.sakthi.cricconnect.model.PlayerStatus;
import com.sakthi.cricconnect.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public Player addPlayer(Player player){
        player.setStatus(PlayerStatus.AVAILABLE);
        return playerRepository.save(player);
    }

    public List<Player> getPlayers(PlayerRole role, String city){
        if(role != null && city != null){
            return playerRepository.findByRoleAndCity(role, city);
        }
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id){
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found!"));
    }
}
