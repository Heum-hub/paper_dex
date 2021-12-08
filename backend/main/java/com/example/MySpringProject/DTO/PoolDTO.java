package com.example.MySpringProject.DTO;

import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class PoolDTO {

  private String poolAddress;

  private String coin1;

  private String coin2;

  private Double balance1;

  private Double balance2;

  private Double txFeeBalance1;

  private Double txFeeBalance2;

  // NoArgsConstructor
  protected PoolDTO() {}

  @Builder
  public PoolDTO(String poolAddress, String coin1, String coin2, Double balance1, Double balance2, Double txFeeBalance1, Double txFeeBalance2) {
    
    this.poolAddress = poolAddress;
    this.coin1 = coin1;
    this.coin2 = coin2;
    this.balance1 = balance1;
    this.balance2 = balance2;
    this.txFeeBalance1 = txFeeBalance1;
    this.txFeeBalance2 = txFeeBalance2;    

  }

}