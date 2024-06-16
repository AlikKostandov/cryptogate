package com.cryptogate.service;

import com.cryptogate.contract.PIP;
import com.cryptogate.converters.BaseUserConverter;
import com.cryptogate.dto.BaseUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PIPService {

    private final PIP pip;

    private final BaseUserConverter baseUserConverter;

    public void addUser(String userAddress, String username, Long role, Long department) throws Exception {
        try {
            pip.addUser(userAddress, username, BigInteger.valueOf(role), BigInteger.valueOf(department)).send();
        } catch (TransactionException e) {
            log.info("Exception reason: " + e.getMessage());
        }
    }


    //
//    public PIP.BaseUser getUser(String userAddress) throws Exception {
//        return pip.getUser(userAddress).send();
//    }
//
    public void removeUser(String userAddress) {
        try {
            pip.removeUser(userAddress).send();
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
        }
    }

    public List<BaseUserEntity> getAllUsers() {
        List<PIP.BaseUser> usersFromBC = null;
        try {
            usersFromBC = pip.getAllUsers().send();
            if (Boolean.FALSE.equals(CollectionUtils.isEmpty(usersFromBC))) {
                return usersFromBC.stream()
                        .map(baseUserConverter::convert).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

}

