package com.example.MySpringProject.Service;

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

import org.springframework.web.bind.annotation.RequestParam;

import com.example.MySpringProject.DAO.Entities.*;
import com.example.MySpringProject.DTO.*;
import java.security.NoSuchAlgorithmException;

public interface DexService {
    
    List<PoolDTO> getPoolAll();

    PoolDTO getPoolByCoin2(String coin2);

    WalletDTO getWalletByUserId(@RequestParam String userId);

    List<BlockDTO> getBlockAll();

    BlockDTO getBlockById(Integer id);  

    ResponseEntity<Response> createWallet (
        @RequestParam String userId,
        @RequestParam String userPw)
        throws NoSuchAlgorithmException;

    ResponseEntity<Response> updatePoolSwap (
        @RequestParam String sellCoinName,
        @RequestParam String buyCoinName,
        @RequestParam Double sellCoinAmount,
        @RequestParam String userId)
        throws NoSuchAlgorithmException;

    ResponseEntity<Response> deposit(
        @RequestParam String coinName,
        @RequestParam Double coinAmount,
        @RequestParam String userId);


    ResponseEntity<Response> farmFee(
        @RequestParam String coinName,
        @RequestParam String userId);

    ResponseEntity<Response> withdrawal(
        @RequestParam String coinName,
        @RequestParam Double coinAmount,
        @RequestParam String userId);




    

}