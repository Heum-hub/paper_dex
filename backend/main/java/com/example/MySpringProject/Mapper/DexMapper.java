package com.example.MySpringProject.Mapper;

import com.example.MySpringProject.DAO.Entities.*;
import com.example.MySpringProject.DAO.Repositories.*;
import com.example.MySpringProject.DTO.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DexMapper {
    
    @Mapping(target = "poolAddress", source = "poolAddress")
    @Mapping(target = "coin1", source = "coin1")
    @Mapping(target = "coin2", source = "coin2")
    @Mapping(target = "balance1", source = "balance1")
    @Mapping(target = "balance2", source = "balance2")
    @Mapping(target = "txFeeBalance1", source = "txFeeBalance1")
    @Mapping(target = "txFeeBalance2", source = "txFeeBalance2")
    PoolDTO toPoolDto(Pool pool);

    @Mapping(target = "poolAddress", source = "poolAddress")
    @Mapping(target = "coin1", source = "coin1")
    @Mapping(target = "coin2", source = "coin2")
    @Mapping(target = "balance1", source = "balance1")
    @Mapping(target = "balance2", source = "balance2")
    @Mapping(target = "txFeeBalance1", source = "txFeeBalance1")
    @Mapping(target = "txFeeBalance2", source = "txFeeBalance2")    
    Pool toPool(PoolDTO poolDto);
    
    @Mapping(target = "walletAddress", source = "walletAddress")
    @Mapping(target = "btcQuantity", source = "btcQuantity")
    @Mapping(target = "ethQuantity", source = "ethQuantity")
    @Mapping(target = "adaQuantity", source = "adaQuantity")
    @Mapping(target = "eosQuantity", source = "eosQuantity")
    @Mapping(target = "omgQuantity", source = "omgQuantity")
    @Mapping(target = "daiQuantity", source = "daiQuantity")
    WalletDTO toWalletDto(Wallet wallet);
    
    @Mapping(target = "walletAddress", source = "walletAddress")
    @Mapping(target = "btcQuantity", source = "btcQuantity")
    @Mapping(target = "ethQuantity", source = "ethQuantity")
    @Mapping(target = "adaQuantity", source = "adaQuantity")
    @Mapping(target = "eosQuantity", source = "eosQuantity")
    @Mapping(target = "omgQuantity", source = "omgQuantity")
    @Mapping(target = "daiQuantity", source = "daiQuantity")
    Wallet toWallet(WalletDTO walletDto);

    @Mapping(target = "number", source = "number")
    @Mapping(target = "hash", source = "hash")
    @Mapping(target = "timeStamp", source = "timeStamp")
    @Mapping(target = "fromAddress", source = "fromAddress")
    @Mapping(target = "toAddress", source = "toAddress")
    @Mapping(target = "coinName", source = "coinName")
    @Mapping(target = "coinAmount", source = "coinAmount")
    BlockDTO toBlockDto(Block block);
    
    @Mapping(target = "hash", source = "hash")
    @Mapping(target = "timeStamp", source = "timeStamp")
    @Mapping(target = "fromAddress", source = "fromAddress")
    @Mapping(target = "toAddress", source = "toAddress")
    @Mapping(target = "coinName", source = "coinName")
    @Mapping(target = "coinAmount", source = "coinAmount")
    Block toBlock(BlockDTO blockDto);    

}
