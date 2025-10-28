package com.sakthi.cricconnect.controller;

import com.sakthi.cricconnect.model.Player;
import com.sakthi.cricconnect.model.PlayerRole;
import com.sakthi.cricconnect.repository.PlayerRepository;
import com.sakthi.cricconnect.service.PlayerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {

    private final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }


     @PostMapping
     public ResponseEntity<Player> addPlayer(@RequestBody Player player){
        return ResponseEntity.ok(playerRepository.save(player));
     }
//    public Player addPlayer(@RequestBody Player player){
//        return playerService.addPlayer(player);
//    }


    @GetMapping
    public ResponseEntity<List<Player>> getPlayers(@RequestParam(required = false) PlayerRole role,
        @RequestParam(required = false) String city){
        if(role !=null && city !=null){
            return ResponseEntity.ok(playerRepository.findByRoleAndCity(role, city));
        }else{
            return ResponseEntity.ok(playerRepository.findAll());
        }
    }
//    public List<Player> getPlayers(@RequestParam(required = false)PlayerRole role,
//                                   @RequestParam(required = false) String city){
//        return playerService.getPlayers(role, city);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id){
        return playerRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
//    public Player getPlayer(@PathVariable Long id){
//        return playerService.getPlayerById(id);
//    }
}
