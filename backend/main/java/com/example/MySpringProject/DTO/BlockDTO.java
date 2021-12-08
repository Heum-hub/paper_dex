package com.example.MySpringProject.DTO;

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
public class BlockDTO {

  private Integer number;

  private String hash;

  private String timeStamp;
  
  private String fromAddress;

  private String toAddress;
  
  private String coinName;

  private Double coinAmount;

  // NoArgsConstructor
  protected BlockDTO() {}

  @Builder(toBuilder = true)
  public BlockDTO(Integer number, String hash, String timeStamp, String fromAddress, String toAddress, String coinName, Double coinAmount) {

    this.number = number;
    this.hash = hash;
    this.timeStamp = timeStamp;
    this.fromAddress = fromAddress;
    this.toAddress = toAddress;
    this.coinName = coinName;
    this.coinAmount = coinAmount;

  }

}