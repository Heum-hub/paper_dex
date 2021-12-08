package com.example.MySpringProject.Controllers;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;

import com.example.MySpringProject.DAO.Entities.*;
import com.example.MySpringProject.DAO.Repositories.*;
import com.example.MySpringProject.DTO.*;
import com.example.MySpringProject.Service.*;

@RestController
@RequestMapping("/dex")
public class DexController {

  @Autowired
  private DexService dexService;

  // 2. GET
  // 2.1. 전체 풀 정보 보기
  @GetMapping("/pool")
  public List<PoolDTO> getPoolAll() {

    try {
      return dexService.getPoolAll();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }
  
  // 2.2. 특정 풀 정보 보기
  @GetMapping("/pool/{coin2}")
  public PoolDTO getPoolByCoin2(@PathVariable("coin2") String coin2) {

    try {
      return dexService.getPoolByCoin2(coin2);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }    

  
  }
  
  // 2.3. 특정 지갑 정보 보기
  @GetMapping("/wallet")
  public WalletDTO getWalletByUserId (
    @RequestParam String userId) {

    try {
      return dexService.getWalletByUserId(userId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }    

  }

  // 2.4. 전체 블록 정보 보기
  @GetMapping("/block")
  public List<BlockDTO> getBlockAll() {

    try {
      return dexService.getBlockAll();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }    

  }

  // 2.5. 특정 블록 정보 보기
  @GetMapping("/block/{id}")
  public BlockDTO getBlockById (
    @PathVariable("id") Integer id) {

    try {
      return dexService.getBlockById(id);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }    

  }
  
 
  // 3. POST
  // 3.1. 신규 가입 시 지갑 생성
  @PostMapping("/createWallet")
  public ResponseEntity<Response> createWallet(
    @RequestParam String userId,
    @RequestParam String userPw
  ) throws NoSuchAlgorithmException{

    try {
      return dexService.createWallet(userId, userPw);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }    

  }


  // 4. PUT  
  // 4.1. 스왑 기능
  // 4.1.1. 풀 잔액 조절(AMM 이용: x * y = k -> x, y는 pool의 코인 각각의 개수, k는 고정 상수)
  // 4.1.2. 지갑 잔액 조절
  // 4.1.3. 수수료 수익 떼가기
  @PutMapping("/swap/result")
  public ResponseEntity<Response> updatePoolSwap(
    @RequestParam String sellCoinName,
    @RequestParam String buyCoinName,
    @RequestParam Double sellCoinAmount,
    @RequestParam String userId  
  ) throws NoSuchAlgorithmException {

    try {
      return dexService.updatePoolSwap(sellCoinName, buyCoinName, sellCoinAmount, userId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }    

  } 

  @PutMapping("/addLiquidity")
  public ResponseEntity<Response> deposit(
      @RequestParam String coinName,
      @RequestParam Double coinAmount,
      @RequestParam String userId) {

    try {
      return dexService.deposit(coinName, coinAmount, userId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @PutMapping("/farmFee")
  public ResponseEntity<Response> farmFee(
      @RequestParam String coinName,
      @RequestParam String userId) {

    try {
      return dexService.farmFee(coinName, userId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  // 4.2. 유동성 회수  
  @PutMapping("/withdrawLiquidity")
  public ResponseEntity<Response> withdrawal(
      @RequestParam String coinName,
      @RequestParam Double coinAmount,
      @RequestParam String userId) {

    try {
      return dexService.withdrawal(coinName, coinAmount, userId);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


} 