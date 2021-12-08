package com.example.MySpringProject.DAO.Entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import com.example.MySpringProject.DAO.Entities.User;


@Getter
@Setter
@Entity
@Table(name = "WALLET")
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "WALLET_ADDRESS")
  private String walletAddress;

  @Column(name = "BTC_QUANTITY")
  private Double btcQuantity;

  @Column(name = "ETH_QUANTITY")
  private Double ethQuantity;

  @Column(name = "ADA_QUANTITY")
  private Double adaQuantity;
  
  @Column(name = "EOS_QUANTITY")
  private Double eosQuantity;
  
  @Column(name = "OMG_QUANTITY")
  private Double omgQuantity;

  @Column(name = "DAI_QUANTITY")
  private Double daiQuantity;

  @Column(name = "USER_ID")
  private String userId;

  // NoArgsConstructor
  protected Wallet() {}

  @Builder(toBuilder = true)
  public Wallet(String walletAddress, Double btcQuantity, Double ethQuantity, Double adaQuantity, Double eosQuantity, Double omgQuantity, Double daiQuantity, String userId) {

    this.walletAddress = walletAddress;
    this.btcQuantity = btcQuantity;
    this.ethQuantity = ethQuantity;
    this.adaQuantity = adaQuantity;
    this.eosQuantity = eosQuantity;
    this.omgQuantity = omgQuantity;
    this.daiQuantity = daiQuantity;
    this.userId = userId;

  }
  
  // 1. 파라미터로 받은 코인 이름으로 지갑에 해당 코인 수량 반환 메소드
  public Double getQuantity(String coinName) {

    if (coinName.equals("BTC")) {

        return getBtcQuantity();

    } else if (coinName.equals("ETH")) {

        return getEthQuantity();

    } else if (coinName.equals("ADA")) {

        return getAdaQuantity();

    } else if (coinName.equals("EOS")) {

        return getEosQuantity();

    } else if (coinName.equals("OMG")) {

        return getOmgQuantity();

    } else if (coinName.equals("DAI")) {

        return getDaiQuantity();

    } else {

        return null;

    }
                                
  }

  // 2.  파라미터로 받은 코인 이름, 수량으로 지갑에 해당 코인 수량 조정 setter
  public void setQuantity(String coinName, Double coinQuantity) {

      if (coinName.equals("BTC")) {

          setBtcQuantity(coinQuantity);

      } else if (coinName.equals("ETH")) {

          setEthQuantity(coinQuantity);

      } else if (coinName.equals("ADA")) {

          setAdaQuantity(coinQuantity);

      } else if (coinName.equals("EOS")) {

          setEosQuantity(coinQuantity);

      } else if (coinName.equals("OMG")) {

          setOmgQuantity(coinQuantity);

      } else if (coinName.equals("DAI")) {

          setDaiQuantity(coinQuantity);

      }
                                
  }

}