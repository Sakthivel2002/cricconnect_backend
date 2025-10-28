package com.sakthi.cricconnect.repository;

import com.sakthi.cricconnect.model.Player;
import com.sakthi.cricconnect.model.PlayerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    List<Player> findByRoleAndCity(PlayerRole role, String city);
    List<Player> findByUser_Id(Long userId);


}
