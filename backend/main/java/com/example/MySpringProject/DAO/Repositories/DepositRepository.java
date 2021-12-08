package com.example.MySpringProject.DAO.Repositories;

import com.example.MySpringProject.DAO.Entities.Deposit;
import com.example.MySpringProject.DAO.Entities.Pool;
import com.example.MySpringProject.DAO.Entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {
  List<Deposit> findAll();
  List<Deposit> findAllByPool(Pool pool);
  List<Deposit> findAllByWallet(Wallet wallet);
  List<Deposit> findAllByWalletAndPool(Wallet wallet, Pool pool);
  Deposit findByWallet(Wallet wallet);
  Deposit findByWalletAndPool(Wallet wallet, Pool pool);
}
