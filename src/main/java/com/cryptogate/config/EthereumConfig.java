package com.cryptogate.config;

import com.cryptogate.contract.PAP;
import com.cryptogate.contract.PIP;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticEIP1559GasProvider;

import java.math.BigInteger;

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
        return new StaticEIP1559GasProvider(0x539,
                BigInteger.valueOf(4_079_367_666L),
                BigInteger.valueOf(4_071_516_296L),
                BigInteger.valueOf(9_000_000L));
    }

    @Bean
    public PIP pip(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        PIP pip;
        try {
            pip = PIP.deploy(web3j, transactionManager, contractGasProvider).send();
        } catch (Exception e) {
            log.error("Error while deploying a contract", e);
            throw new RuntimeException(e);
        }
        log.info("PIP contract has been deployed: {}", pip.getContractAddress());
        return pip;
    }

    @Bean
    public PAP pap(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, PIP pip) {
        PAP pap;
        try {
            pap = PAP.deploy(web3j, transactionManager, contractGasProvider, pip.getContractAddress()).send();
//            pap.registerUser("0xc2552c34C318C1BC170d4D03E790473e9abdDc10",
//                    "admin",
//                    "$2a$12$SqJnabBDlshCMlcLsKPiu.Uno3QTwQsdudqjupVFfCe1aHMIQeRRa",
//                    BigInteger.valueOf(Role.ADMIN.getId()),
//                    BigInteger.valueOf(Department.IT.getDepId())).send();
        } catch (Exception e) {
            log.error("Error while deploying a contract", e);
            throw new RuntimeException(e);
        }
        log.info("PAP contract has been deployed: {}", pap.getContractAddress());
        return pap;
    }
}
