package com.example.MySpringProject.DTO;

import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class WalletDTO {

    private String walletAddress;

    private Double btcQuantity;
    
    private Double ethQuantity;
  
    private Double adaQuantity;
    
    private Double eosQuantity;
    
    private Double omgQuantity;
  
    private Double daiQuantity;

    private String userId;

    // NoArgsConstructor
    protected WalletDTO() {}

    @Builder
    public WalletDTO(String walletAddress, Double btcQuantity, Double ethQuantity, Double adaQuantity, Double eosQuantity, Double omgQuantity, Double daiQuantity, String userId) {
      
      this.walletAddress = walletAddress;
      this.btcQuantity = btcQuantity;
      this.ethQuantity = ethQuantity;
      this.adaQuantity = adaQuantity;
      this.eosQuantity = eosQuantity;
      this.omgQuantity = omgQuantity;
      this.daiQuantity = daiQuantity;
      this.userId = userId;
  
    }
      
  }