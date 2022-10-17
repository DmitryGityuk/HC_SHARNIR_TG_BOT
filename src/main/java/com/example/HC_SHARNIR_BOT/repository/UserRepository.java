package com.example.HC_SHARNIR_BOT.repository;


import com.example.HC_SHARNIR_BOT.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
