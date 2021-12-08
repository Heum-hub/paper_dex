package com.example.MySpringProject.DAO.Repositories;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.MySpringProject.DAO.Entities.Block;

public interface BlockRepository extends JpaRepository<Block, Integer> {
    
    List<Block> findAll();

}