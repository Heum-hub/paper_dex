package com.example.MySpringProject.DAO.Repositories;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.MySpringProject.DAO.Entities.User;


public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}