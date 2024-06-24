package com.cryptogate.contract.service;

import com.cryptogate.contract.PDP;
import com.cryptogate.contract.PEP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

@Slf4j
@Service
@RequiredArgsConstructor
public class PEPService {

    private final PDP pdp;
    private final Web3j web3j;
    private final ContractGasProvider contractGasProvider;

    @Value("${ethereum.contract-address.pep}")
    private String pepAddress;

    public Boolean checkAccess(String userAddress, String sourceId) throws Exception {
        PEP pep = loadPepContract(userAddress);
        return pep.requestAccess(sourceId).send();
    }

    private PEP loadPepContract(String userAddress) {
        TransactionManager transactionManager = new ClientTransactionManager(web3j, userAddress);
        PEP pep;
        try {
            pep = PEP.load(pepAddress, web3j, transactionManager, contractGasProvider);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading PRP contract", e);
        }
        return pep;
    }

}
