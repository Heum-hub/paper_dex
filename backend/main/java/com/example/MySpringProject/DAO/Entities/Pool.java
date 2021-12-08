package com.example.MySpringProject.DAO.Entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;


@Getter
@Setter
@Entity
@Table(name = "POOL")
public class Pool {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "POOL_ADDRESS")
  private String poolAddress;

  @Column(name = "COIN1")
  private String coin1;

  @Column(name = "COIN2")
  private String coin2;

  @Column(name = "BALANCE1")
  private Double balance1;

  @Column(name = "BALANCE2")
  private Double balance2;

  @Column(name = "TX_FEE_BALANCE1")
  private Double txFeeBalance1;

  @Column(name = "TX_FEE_BALANCE2")
  private Double txFeeBalance2;

  // NoArgsConstructor
  protected Pool() {}

  @Builder(toBuilder = true)
  public Pool(String poolAddress, String coin1, String coin2, Double balance1, Double balance2, Double txFeeBalance1, Double txFeeBalance2) {

    this.poolAddress = poolAddress;
    this.coin1 = coin1;
    this.coin2 = coin2;
    this.balance1 = balance1;
    this.balance2 = balance2;
    this.txFeeBalance1 = txFeeBalance1;
    this.txFeeBalance2 = txFeeBalance2;

  }

}