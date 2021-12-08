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
@Table(name = "BLOCK")
public class Block {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer number;

  @Column(name = "BLOCK_HASH")
  private String hash;

  @Column(name = "TIME_STAMP")
  private String timeStamp;
  
  @Column(name = "FROM_ADDRESS")
  private String fromAddress;

  @Column(name = "TO_ADDRESS")
  private String toAddress;
  
  @Column(name = "COIN_NAME")
  private String coinName;

  @Column(name = "COIN_AMOUNT")
  private Double coinAmount;

  // NoArgsConstructor
  protected Block() {}

  @Builder(toBuilder = true)
  public Block(String hash, String timeStamp, String fromAddress, String toAddress, String coinName, Double coinAmount) {

    this.hash = hash;
    this.timeStamp = timeStamp;
    this.fromAddress = fromAddress;
    this.toAddress = toAddress;
    this.coinName = coinName;
    this.coinAmount = coinAmount;

  }

}