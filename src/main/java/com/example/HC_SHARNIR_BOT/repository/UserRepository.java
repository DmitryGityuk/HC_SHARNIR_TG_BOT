package com.example.HC_SHARNIR_BOT.repository;


import com.example.HC_SHARNIR_BOT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM players_data_table ORDER BY assists DESC LIMIT 3")
    List<User> getUserByAssists();

    @Query(nativeQuery = true, value = "SELECT * FROM players_data_table ORDER BY comb DESC LIMIT 3")
    List<User> getUserByComb();

    @Query(nativeQuery = true, value = "SELECT * FROM players_data_table ORDER BY goals DESC LIMIT 3")
    List<User> getUserByGoals();
}
