package com.example.MySpringProject.DAO.Entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "DEPOSIT")
public class Deposit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name = "pool_id", updatable = false, nullable = false)
  private Pool pool;

  @OneToOne
  @JoinColumn(name = "wallet_id", updatable = false, nullable = false)
  private Wallet wallet;

  @Column(name = "COIN1SUPPLY")
  private Double shareratio;

  protected Deposit() {}

  @Builder(toBuilder = true)
  public Deposit(Wallet wallet, Pool pool, Double shareratio) {
    this.wallet = wallet;
    this.pool = pool;
    this.shareratio = shareratio;
  }

  public void modify(Double ratio){
    this.shareratio = ratio;
  }

}
