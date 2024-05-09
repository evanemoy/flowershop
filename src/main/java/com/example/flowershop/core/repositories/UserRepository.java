package com.example.flowershop.core.repositories;

import com.example.flowershop.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUserName(String userName);
}
