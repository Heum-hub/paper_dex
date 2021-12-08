package com.example.MySpringProject.Service.Impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.lang.NullPointerException;

import com.example.MySpringProject.DAO.Entities.*;
import com.example.MySpringProject.DAO.Repositories.*;
import com.example.MySpringProject.DTO.*;
import com.example.MySpringProject.Service.DexService;
import com.example.MySpringProject.Mapper.*;


@Service
public class DexServiceImpl implements DexService {

    @Autowired
    private PoolRepository poolRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private DexMapper mapper;
    

    // 1. 지갑, 풀, 블록 주소 만들기 위한 메서드
    // 1.1. SHA 256 해싱  
    private String sha256(String msg) throws NoSuchAlgorithmException {
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        return bytesToHex(md.digest());
                  
    }
    
    // 1.2. 바이트 값 헥스 변환
    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
        builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    // 1.3. 타임스탬프 반환
    private String timeStamp() {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss")
        .format(new Timestamp(System.currentTimeMillis()));

        return timeStamp;

    }    


    // 2. GET
    // 2.1. 전체 풀 정보 보기
    @Override
    public List<PoolDTO> getPoolAll() {

        List<Pool> pool = poolRepository.findAll();

        return pool.stream()
                .map(mapper::toPoolDto)
                .collect(Collectors.toList());

    }
    
    // 2.2. 특정 풀 정보 보기
    @Override
    public PoolDTO getPoolByCoin2(String coin2) {

        Pool pool = poolRepository.findByCoin2(coin2);
    
        return mapper.toPoolDto(pool);
  
    }
  
    // 2.3. 특정 지갑 정보 보기
    @Override
    public WalletDTO getWalletByUserId(
        @RequestParam String userId) {
        
        Wallet wallet = walletRepository.findByUserId(userId);

        return mapper.toWalletDto(wallet);

    }    

    // 2.4. 전체 블록 정보 보기
    @Override
    public List<BlockDTO> getBlockAll() {

        List<Block> block = blockRepository.findAll();

        return block.stream()
                .map(mapper::toBlockDto)
                .collect(Collectors.toList());

    }

    // 2.5. 특정 블록 정보 보기
    @Override
    public BlockDTO getBlockById(Integer id) {
        
        Block block = blockRepository.findById(id).get();

        return mapper.toBlockDto(block);

    }    


    // 3. POST
    // 3.1. 신규 가입 시 지갑 생성(각 코인 100개씩 부여)
    @Override
    @Transactional
    public ResponseEntity<Response> createWallet(
        @RequestParam String userId,
        @RequestParam String userPw
    ) throws NoSuchAlgorithmException {
  
      WalletDTO walletDto = WalletDTO.builder()
      .walletAddress("0x" + sha256(userPw).substring(24, 64))
      .btcQuantity(100.0)
      .ethQuantity(100.0)
      .adaQuantity(100.0)
      .eosQuantity(100.0)
      .omgQuantity(100.0)
      .daiQuantity(100.0)
      .userId(userId)
      .build();
      
      Wallet wallet = mapper.toWallet(walletDto);

      walletRepository.save(wallet);

      Response response = new Response ("success",
      "가입 기념 지갑이 발급되었습니다. 로그인 후 '내 지갑 보기'에서 확인 가능합니다.",
      null);
 
      return new ResponseEntity<Response> (
          response,
          HttpStatus.OK);

    }

    // 4. PUT  
    // 4.1. 스왑 기능
    // 4.1.1. 필요 변수, 메소드 선언(AMM 이용: x * y = k
    // -> x, y는 pool의 코인 각각의 개수, k는 고정 상수)
    private double k;
    private double beforeSwapBtcBalance;
    private double buyCoinAmount;
    private double buyAmount;
    // prevBlock() -> 바로 직전의 블록 정보 반환
    private Block prevBlock() {

        return blockRepository.findById(blockRepository.findAll().size()).get();

    }
    // blockCreate() -> 트랜잭션 기록 블록 생성
    private void blockCreate(
        Wallet walletToUpdate, Pool poolToUpdate,
        String sellCoinName, String buyCoinName,
        Double sellCoinAmount, Double buyCoinAmount) 
        throws NoSuchAlgorithmException {

        Block blockSell = Block.builder()
        .hash("0x" + sha256(prevBlock().getHash() +
        timeStamp() + walletToUpdate.getWalletAddress() +
        poolToUpdate.getPoolAddress() + sellCoinName +
        String.valueOf(sellCoinAmount/0.99)))
        .timeStamp(timeStamp())
        .fromAddress(walletToUpdate.getWalletAddress())
        .toAddress(poolToUpdate.getPoolAddress())
        .coinName(sellCoinName)
        .coinAmount(sellCoinAmount/0.99)
        .build();

        blockRepository.save(blockSell);
       
        Block blockBuy = Block.builder()
        .hash("0x" + sha256(prevBlock().getHash() +
        timeStamp() + poolToUpdate.getPoolAddress() +
        walletToUpdate.getWalletAddress() + buyCoinName +
        String.valueOf(buyCoinAmount)))
        .timeStamp(timeStamp())
        .fromAddress(poolToUpdate.getPoolAddress())
        .toAddress(walletToUpdate.getWalletAddress())
        .coinName(buyCoinName)
        .coinAmount(buyCoinAmount)
        .build();

        blockRepository.save(blockBuy);

    }
    // 4.1.3. 풀 잔액 조절
    // 4.1.4. 지갑 잔액 조절
    // 4.1.5. 수수료 수익 떼가기
    // 4.1.6. 거래 기록하는 장부인 블록 생성
    @Override
    @Transactional
    public ResponseEntity<Response> updatePoolSwap(
        @RequestParam String sellCoinName,
        @RequestParam String buyCoinName,
        @RequestParam Double sellCoinAmount,
        @RequestParam String userId) 
        throws NoSuchAlgorithmException {

        Wallet walletToUpdate = walletRepository.findByUserId(userId);
        
        if ( walletToUpdate.getQuantity(sellCoinName) < sellCoinAmount) {

            Response response = new Response (
                "failed", "매도 수량이 지갑에 있는 보유량을 초과하였습니다.", null);

            return new ResponseEntity<Response> (
                response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        
        // 1. 매수/매도 코인 한 쪽이 BTC일 경우
        if (sellCoinName.equals("BTC")) {

            // 1.1. 지갑에서 매도 코인 수량 인출
            walletToUpdate.setBtcQuantity(
                walletToUpdate.getBtcQuantity() - sellCoinAmount);

            // 1.2. 매도 코인 수량에서 1% 수수료 취득
            Pool poolToUpdate = poolRepository.findByCoin2(buyCoinName);;
      
            sellCoinAmount = sellCoinAmount * 0.99;
      
            poolToUpdate.setTxFeeBalance1(sellCoinAmount * 0.01);
            
            // 1.3. 수수료 제한 매도 수량 해당 잔고(한 쪽 잔고)에 추가
            k = poolToUpdate.getBalance1() * poolToUpdate.getBalance2(); 
      
            poolToUpdate.setBalance1(poolToUpdate.getBalance1() + sellCoinAmount);

            // 1.4. 지갑에 매수 코인 수량 입금
            buyCoinAmount = (poolToUpdate.getBalance2() - k/poolToUpdate.getBalance1());

            walletToUpdate.setQuantity(
                buyCoinName,
                walletToUpdate.getQuantity(buyCoinName)
                + buyCoinAmount);

            // 1.5. 반대 쪽 잔고 수량 조절
            poolToUpdate.setBalance2(k/poolToUpdate.getBalance1());
            
            // 1.6. 거래 내역(매도 및 매수) 기록하는 블록 생성 및 저장
            blockCreate(
                walletToUpdate, poolToUpdate,
                sellCoinName, buyCoinName,
                sellCoinAmount, buyCoinAmount);

            // 1.7. 변경 사항 지갑, 풀에 저장
            walletRepository.save(walletToUpdate);
            poolRepository.save(poolToUpdate);       


            Response response = new Response (
                "success", "스왑이 완료되었습니다.", null);

            return new ResponseEntity<Response> (
                response, HttpStatus.OK);
          
        }
      
        else if (buyCoinName.equals("BTC")) {

            // 1.1. 지갑에서 매도 코인 수량 인출
            walletToUpdate.setQuantity(
                sellCoinName,
                walletToUpdate.getQuantity(sellCoinName) - sellCoinAmount);

            // 1.2. 매도 코인 수량에서 1% 수수료 취득            
            Pool poolToUpdate = poolRepository.findByCoin2(sellCoinName);;
      
            sellCoinAmount = sellCoinAmount * 0.99;
      
            poolToUpdate.setTxFeeBalance2(sellCoinAmount * 0.01);
            
            // 1.3. 수수료 제한 매도 수량 해당 잔고(한 쪽 잔고)에 추가
            k = poolToUpdate.getBalance1() * poolToUpdate.getBalance2();
      
            poolToUpdate.setBalance2(poolToUpdate.getBalance2() + sellCoinAmount);
            
            // 1.4. 지갑에 매수 코인 수량 입금
            buyCoinAmount = (poolToUpdate.getBalance1() - k/poolToUpdate.getBalance2());

            walletToUpdate.setBtcQuantity(
                walletToUpdate.getBtcQuantity()
                + buyCoinAmount);            
            
            // 1.5. 반대 쪽 잔고 수량 조절
            poolToUpdate.setBalance1(k/poolToUpdate.getBalance2());

            // 1.6. 거래 내역(매도 및 매수) 기록하는 블록 생성 및 저장
            blockCreate(
                walletToUpdate, poolToUpdate,
                sellCoinName, buyCoinName,
                sellCoinAmount, buyCoinAmount);
            
            // 1.7. 변경 사항 지갑, 풀에 저장
            walletRepository.save(walletToUpdate);            
            poolRepository.save(poolToUpdate);

            Response response = new Response (
                "success", "스왑이 완료되었습니다.", null);

            return new ResponseEntity<Response> (
                response, HttpStatus.OK);
          
        }
        
        // 2. 매수/매도 코인 양 쪽 다 BTC가 아닐 경우
        else {

            // 2.1. 팔고자 하는 코인을 먼저 BTC와 스왑
            // 2.1.1. 지갑에서 매도 코인 수량 인출
            walletToUpdate.setQuantity(
                sellCoinName,
                walletToUpdate.getQuantity(sellCoinName) - sellCoinAmount);

            // 2.1.2. 매도 코인 수량에서 1% 수수료 취득     
            Pool poolToUpdateSell = poolRepository.findByCoin2(sellCoinName);
      
            sellCoinAmount = sellCoinAmount * 0.99;
      
            poolToUpdateSell.setTxFeeBalance2(sellCoinAmount * 0.01);
            
            // 2.1.3. 수수료 제한 매도 수량 해당 잔고(한 쪽 잔고)에 추가            
            k = poolToUpdateSell.getBalance1() * poolToUpdateSell.getBalance2(); 
      
            poolToUpdateSell.setBalance2(poolToUpdateSell.getBalance2() + sellCoinAmount);
            
            // 2.1.4. btc balance 조정 전에 변수로 저장(2.1에서의 buyCoinAmount 및 2.2에서 sellCoinAmount 구하기 위함)
            beforeSwapBtcBalance = poolToUpdateSell.getBalance1();
            
            // 2.1.5 반대 쪽 잔고 수량 조절                        
            poolToUpdateSell.setBalance1(k/poolToUpdateSell.getBalance2());

            // 2.1.6. 거래 내역(매도 및 매수) 기록하는 블록 생성 및 저장
            blockCreate(
                walletToUpdate, poolToUpdateSell,
                sellCoinName, "BTC", sellCoinAmount, 
                beforeSwapBtcBalance - poolToUpdateSell.getBalance1());
                           
            // 2.2. 스왑해서 얻은 BTC로 구매하고자 하는 코인을 구매
            Pool poolToUpdateBuy = poolRepository.findByCoin2(buyCoinName);
            
            // 2.2.1. 판매 수량 구하기(2.1에서 얻은 btc 수량): 스왑 전 btcBalance - 스왑 후 btcBalance
            sellCoinAmount = (beforeSwapBtcBalance - poolToUpdateSell.getBalance1()) * 0.99;
            
            // 2.2.2. 매도 코인 수량에서 1% 수수료 취득      
            poolToUpdateBuy.setTxFeeBalance1(sellCoinAmount * 0.01);

            // 2.2.3. 수수료 제한 매도 수량 해당 잔고(한 쪽 잔고)에 추가            
            k = poolToUpdateBuy.getBalance1() * poolToUpdateBuy.getBalance2(); 
      
            poolToUpdateBuy.setBalance1(poolToUpdateBuy.getBalance1() + sellCoinAmount);

            // 2.2.4. 지갑에 매수 코인 수량 입금
            buyCoinAmount = poolToUpdateBuy.getBalance2() - k/poolToUpdateBuy.getBalance1();

            walletToUpdate.setQuantity(
                buyCoinName,
                walletToUpdate.getQuantity(buyCoinName)
                +buyCoinAmount);

            // 2.2.5 반대 쪽 잔고 수량 조절            
            poolToUpdateBuy.setBalance2(k/poolToUpdateBuy.getBalance1());

            // 2.2.6. 거래 내역(매도 및 매수) 기록하는 블록 생성 및 저장
            blockCreate(
                walletToUpdate, poolToUpdateBuy,
                "BTC", buyCoinName,
                sellCoinAmount, buyCoinAmount);

            // 2.2.7. 변경 사항 지갑, 풀에 저장            
            walletRepository.save(walletToUpdate);
            poolRepository.save(poolToUpdateSell);
            poolRepository.save(poolToUpdateBuy);
            
            Response response = new Response (
                "success", "스왑이 완료되었습니다.", null);

            return new ResponseEntity<Response> (
                response, HttpStatus.OK);
      
        }

    }

    // 4.2. 유동성 예치
    private void depositCreate(
        Wallet walletToUpdate, Pool poolToUpdate,
        Double shareratio) {

        Deposit newDeposit = Deposit.builder()
            .wallet(walletToUpdate)
            .pool(poolToUpdate)
            .shareratio(shareratio)
            .build();

        depositRepository.save(newDeposit);
    }
    @Override
    @Transactional
    public ResponseEntity<Response> deposit(
        @RequestParam String coinName,
        @RequestParam Double coinAmount,
        @RequestParam String userId
    ){
        Wallet wallet = walletRepository.findByUserId(userId);
        Pool pool = poolRepository.findByCoin2(coinName);

        Double Balance = pool.getBalance1() / pool.getBalance2();
        Double btcAmount = coinAmount * Balance;

        if(wallet.getBtcQuantity() < btcAmount || wallet.getQuantity(coinName) < coinAmount){
            Response response = new Response (
                "failed", "예치 수량이 지갑에 있는 보유량을 초과하였습니다.", null);

            return new ResponseEntity<Response> (
                response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(depositRepository.findAllByWallet(wallet).isEmpty()){
            List<Deposit> depositList = depositRepository.findAllByPool(pool);

            depositList.forEach(deposit -> {
                Double supply = pool.getBalance1() * deposit.getShareratio();
                Double newRatio = supply / (pool.getBalance1() + btcAmount);
                deposit.modify(newRatio);
                depositRepository.save(deposit);
            });

            Double shareRatio = btcAmount /(pool.getBalance1() + btcAmount);

            wallet.setQuantity("BTC", wallet.getBtcQuantity() - btcAmount);
            wallet.setQuantity(coinName, wallet.getQuantity(coinName) - coinAmount);

            pool.setBalance1(pool.getBalance1() + btcAmount);
            pool.setBalance2(pool.getBalance2() + coinAmount);

            walletRepository.save(wallet);
            poolRepository.save(pool);

            depositCreate(wallet, pool, shareRatio);
        }else{
            List<Deposit> depositList = depositRepository.findAllByPool(pool);

            depositList.forEach(deposit -> {
                if(deposit.getWallet().getId() == wallet.getId()){
                    Double supply = pool.getBalance1() * deposit.getShareratio();
                    Double newRatio = (supply + btcAmount) / (pool.getBalance1() + btcAmount);
                    deposit.modify(newRatio);
                    depositRepository.save(deposit);
                }else{
                    Double supply = pool.getBalance1() * deposit.getShareratio();
                    Double newRatio = supply / (pool.getBalance1() + btcAmount);
                    deposit.modify(newRatio);
                    depositRepository.save(deposit);
                }
            });

            Double shareRatio = btcAmount /(pool.getBalance1() + btcAmount);

            wallet.setQuantity("BTC", wallet.getBtcQuantity() - btcAmount);
            wallet.setQuantity(coinName, wallet.getQuantity(coinName) - coinAmount);

            pool.setBalance1(pool.getBalance1() + btcAmount);
            pool.setBalance2(pool.getBalance2() + coinAmount);

            walletRepository.save(wallet);
            poolRepository.save(pool);
        }



        Response response = new Response("success", "예치가 완료되었습니다.", null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    
    // 4.3. 이자 농사(예치한 유동성 풀에 대한 거래 수수료를 수취하는 메소드)
    @Override
    @Transactional
    public ResponseEntity<Response> farmFee(
        @RequestParam String coinName,
        @RequestParam String userId
    ) {

        Pool poolToUpdate = poolRepository.findByCoin2(coinName);
        Wallet walletToUpdate = walletRepository.findByUserId(userId);
        Deposit depositToUpdate = depositRepository.findByWalletAndPool(walletToUpdate, poolToUpdate);
        
        Double fee1 = poolToUpdate.getTxFeeBalance1();
        Double fee2 =  poolToUpdate.getTxFeeBalance2();
        Double ratio = depositToUpdate.getShareratio();
        
        walletToUpdate.setBtcQuantity(
            walletToUpdate.getBtcQuantity() + (fee1*ratio));
        
        walletToUpdate.setQuantity(
            coinName,
            walletToUpdate.getQuantity(coinName) + (fee2*ratio));
                    
        poolToUpdate.setTxFeeBalance1(
            fee1 - (fee1*ratio));
        
        poolToUpdate.setTxFeeBalance2(
                    fee2 - (fee2*ratio));
        
        walletRepository.save(walletToUpdate);
        poolRepository.save(poolToUpdate);
            

        Response response = new Response (
            "success", "이자 지급이 완료되었습니다.", null);

        return new ResponseEntity<Response> (
            response, HttpStatus.OK);

    }

    //4.4. 예치한 유동성 회수  
    @Override
    @Transactional
    public ResponseEntity<Response> withdrawal(
        @RequestParam String coinName,
        @RequestParam Double coinAmount,
        @RequestParam String userId) {

        Wallet wallet = walletRepository.findByUserId(userId);
        Pool pool = poolRepository.findByCoin2(coinName);
        Deposit depositBywalletAndPool = depositRepository.findByWalletAndPool(wallet, pool);
        Double coin2holdings = pool.getBalance2() * depositBywalletAndPool.getShareratio();

        if(coin2holdings < coinAmount){
            Response response = new Response (
                "failed", "풀에 예치한 코인량을 초과했습니다", null);

            return new ResponseEntity<Response> (
                response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Deposit> depositList = depositRepository.findAllByPool(pool);

        depositList.forEach(deposit -> {
            if(deposit.getWallet().getId() == wallet.getId()){
                if(coinAmount == coin2holdings) {
                    depositRepository.delete(deposit);
                }else {
                    Double supply = pool.getBalance2() * deposit.getShareratio();
                    Double newRatio = (supply - coinAmount) / (pool.getBalance2() - coinAmount);
                    deposit.modify(newRatio);
                    depositRepository.save(deposit);
                }
            }else {
                Double supply = pool.getBalance2() * deposit.getShareratio();
                Double newRatio = supply / (pool.getBalance2() - coinAmount);
                deposit.modify(newRatio);
                depositRepository.save(deposit);
            }
        });

        Double balance = pool.getBalance1() / pool.getBalance2();
        Double btcAmount = coinAmount * balance;

        wallet.setQuantity("BTC", wallet.getBtcQuantity() + btcAmount);
        wallet.setQuantity(coinName, wallet.getQuantity(coinName) + coinAmount);

        pool.setBalance1(pool.getBalance1() - btcAmount);
        pool.setBalance2(pool.getBalance2() - coinAmount);

        walletRepository.save(wallet);
        poolRepository.save(pool);

        Response response = new Response("success", "유동성 회수가 완료되었습니다", null);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    

   

}