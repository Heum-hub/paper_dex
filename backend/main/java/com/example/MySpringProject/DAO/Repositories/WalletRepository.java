package com.example.MySpringProject.DAO.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.MySpringProject.DAO.Entities.Wallet;
import com.example.MySpringProject.DTO.*;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    List<Wallet> findAll();
    
    Wallet findByWalletAddress(String walletAddress);

    Wallet findByUserId(String userId);

}