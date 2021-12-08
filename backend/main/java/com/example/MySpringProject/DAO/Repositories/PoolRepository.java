package com.example.MySpringProject.DAO.Repositories;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.MySpringProject.DAO.Entities.Pool;

public interface PoolRepository extends JpaRepository<Pool, Integer> {
    
    List<Pool> findAll();
    
    Pool findByCoin2(String coin2);

}