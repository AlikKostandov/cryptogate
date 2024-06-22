package com.cryptogate.config;

import com.cryptogate.contract.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

@Slf4j
@Configuration
public class EthereumConfig {

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService("http://localhost:8545")); // URL вашего узла
    }

    @Bean
    public TransactionManager transactionManager(Web3j web3j, @Value("${ethereum.admin.account-address}") String adminAddress) {
        return new ClientTransactionManager(web3j, adminAddress);
    }

    @Bean
    public ContractGasProvider contractGasProvider() {
        return new DefaultGasProvider();
    }

    @Bean
    public PRP prp(Web3j web3j,
                   TransactionManager transactionManager,
                   ContractGasProvider contractGasProvider,
                   @Value("${ethereum.contract-address.prp}") String prpAddress) {
        PRP prp;
        try {
            prp = PRP.load(prpAddress, web3j, transactionManager, contractGasProvider);
        } catch (Exception e) {
            log.error("Error while deploying a contract", e);
            throw new RuntimeException(e);
        }
        log.info("PRP contract has been deployed: {}", prp.getContractAddress());
        return prp;
    }

    @Bean
    public PAP pap(Web3j web3j,
                   TransactionManager transactionManager,
                   ContractGasProvider contractGasProvider,
                   @Value("${ethereum.contract-address.pap}") String papAddress,
                   PRP prp) {
        PAP pap;
        try {
            pap = PAP.load(papAddress, web3j, transactionManager, contractGasProvider);
            prp.setPAPAddress(papAddress);
        } catch (Exception e) {
            log.error("Error while deploying a contract", e);
            throw new RuntimeException(e);
        }
        log.info("PAP contract has been deployed: {}", pap.getContractAddress());
        return pap;
    }

    @Bean
    public PIP pip(Web3j web3j,
                   TransactionManager transactionManager,
                   ContractGasProvider contractGasProvider,
                   @Value("${ethereum.contract-address.pip}") String pipAddress) {
        PIP pip;
        try {
            pip = PIP.load(pipAddress, web3j, transactionManager, contractGasProvider);
        } catch (Exception e) {
            log.error("Error while deploying a contract", e);
            throw new RuntimeException(e);
        }
        log.info("PIP contract has been deployed: {}", pip.getContractAddress());
        return pip;
    }

//    @Bean
//    public PDP pdp(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, PIP pip, PRP prp) {
//        PDP pdp;
//        try {
//            pdp = PDP.deploy(web3j, transactionManager, contractGasProvider, pip.getContractAddress(), prp.getContractAddress()).send();
//        } catch (Exception e) {
//            log.error("Error while deploying a contract", e);
//            throw new RuntimeException(e);
//        }
//        log.info("PDP contract has been deployed: {}", prp.getContractAddress());
//        return pdp;
//    }
//
//    @Bean
//    public PEP pep(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, PDP pdp) {
//        PEP pep;
//        try {
//            pep = PEP.deploy(web3j, transactionManager, contractGasProvider, pdp.getContractAddress()).send();
//            pdp.setPEPAddress(pep.getContractAddress());
//        } catch (Exception e) {
//            log.error("Error while deploying a contract", e);
//            throw new RuntimeException(e);
//        }
//        log.info("PEP contract has been deployed: {}", pep.getContractAddress());
//        return pep;
//    }

}
