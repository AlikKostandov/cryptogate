package com.cryptogate.service;

import com.cryptogate.config.EthereumConnector;
import com.cryptogate.contract.PAP;
import com.cryptogate.dto.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class PAPService {

    private PAP pap;

    public PAPService(String adminAddress, String contractAddress, String rpcEndpoint) {
        EthereumConnector ethereumConnector = new EthereumConnector();
        Web3j web3j = ethereumConnector.getWeb3();
        TransactionManager transactionManager = new ClientTransactionManager(web3j, adminAddress);
        ContractGasProvider gasProvider = new DefaultGasProvider();
        this.pap = PAP.load(contractAddress, web3j, transactionManager, gasProvider);
    }

    public TransactionReceipt registerUser(BaseUser userDto) throws Exception {
        BigInteger roleValue = BigInteger.valueOf(userDto.getRole().getId());
        BigInteger departmentValue = BigInteger.valueOf(userDto.getDepartment().getDepId());
        RemoteCall<TransactionReceipt> remoteCall = pap.registerUser(userDto.getUserAddress(), userDto.getUsername(), roleValue, departmentValue);
        return remoteCall.send();
    }

    public PAP.BaseUser getUser(String userAddress) throws Exception {
        RemoteCall<PAP.BaseUser> remoteCall = pap.getUser(userAddress);
        return remoteCall.send();
    }
}
