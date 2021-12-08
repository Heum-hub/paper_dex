package com.example.MySpringProject.DTO;

import lombok.Builder;
import lombok.Getter;


@Getter
public class DepositDTO {

  private String walletAddress;

  private String poolAddress;

  private Double shareRatio;

  protected DepositDTO() {}

  @Builder(toBuilder = true)
  public DepositDTO(String walletAddress, String poolAddress, Double shareRatio){
    this.walletAddress = walletAddress;
    this.poolAddress = poolAddress;
    this.shareRatio = shareRatio;
  }
}
