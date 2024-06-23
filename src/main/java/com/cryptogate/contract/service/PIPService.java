package com.cryptogate.contract.service;

import com.cryptogate.contract.PIP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PIPService {

    private final PIP pip;

    public TransactionReceipt addUser(String userAddress, String username,
                                      BigInteger role, BigInteger department) throws Exception {
        return pip.addUser(userAddress, username, role, department).send();
    }

    public PIP.BaseUser getUser(String userAddress) throws Exception {
        return pip.getUser(userAddress).send();
    }

    public TransactionReceipt removeUser(String userAddress) throws Exception {
        return pip.removeUser(userAddress).send();
    }

    public List<PIP.BaseUser> getAllUsers() throws Exception {
        return pip.getAllUsers().send();
    }

    public TransactionReceipt addSource(String sourceId, String title, String owner,
                                        BigInteger sourceType, BigInteger secretLevel,
                                        String allowedUsers) throws Exception {
        return pip.addSource(sourceId, title, owner, sourceType, secretLevel, allowedUsers).send();
    }

    public PIP.Source getSource(String sourceId) throws Exception {
        return pip.getSource(sourceId).send();
    }

    public TransactionReceipt removeSource(String sourceId) throws Exception {
        return pip.removeSource(sourceId).send();
    }

    public List<PIP.Source> getAllSources() throws Exception {
        return pip.getAllSources().send();
    }
}

