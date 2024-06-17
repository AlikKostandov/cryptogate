package com.cryptogate.service;

import com.cryptogate.contract.PIP;
import com.cryptogate.converters.BaseUserConverter;
import com.cryptogate.converters.SourceConverter;
import com.cryptogate.dto.BaseUserEntity;
import com.cryptogate.dto.Source;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PIPService {

    private final PIP pip;

    private final BaseUserConverter baseUserConverter;

    private final SourceConverter sourceConverter;

    public void addUser(String userAddress, String username,
                        Long role, Long department) throws Exception {
        try {
            pip.addUser(userAddress, username,
                    BigInteger.valueOf(role),
                    BigInteger.valueOf(department)).send();
        } catch (TransactionException e) {
            log.info("Exception reason: " + e.getMessage());
        }
    }

    public PIP.BaseUser getUser(String userAddress) throws Exception {
        return pip.getUser(userAddress).send();
    }

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

    public void addSource(String owner, String title,
                          Long sourceType, Long secretLevel) {
        try {
            String sourceId = UUID.randomUUID().toString();
            pip.addSource(sourceId, title, owner,
                    BigInteger.valueOf(sourceType),
                    BigInteger.valueOf(secretLevel)).send();
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
        }
    }

    public PIP.Source getSource(String sourceId) throws Exception {
        return pip.getSource(sourceId).send();
    }

    public void removeSource(String sourceId) {
        try {
            pip.removeSource(sourceId).send();
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
        }
    }

    public List<Source> getAllSources() {
        List<PIP.Source> sourcesFromBC = null;
        try {
            sourcesFromBC = pip.getAllSources().send();
            if (Boolean.FALSE.equals(CollectionUtils.isEmpty(sourcesFromBC))) {
                return sourcesFromBC.stream()
                        .map(sourceConverter::convert).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

}

