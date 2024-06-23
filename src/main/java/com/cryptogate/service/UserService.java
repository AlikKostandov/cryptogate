package com.cryptogate.service;

import com.cryptogate.contract.PIP;
import com.cryptogate.contract.service.PIPService;
import com.cryptogate.converters.BaseUserConverter;
import com.cryptogate.dto.BaseUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PIPService pipService;

    private final BaseUserConverter baseUserConverter;

    public void registerUser(String userAddress, String username,
                             Long role, Long department) {
        try {
            TransactionReceipt transaction = pipService.addUser(
                    userAddress, username,
                    BigInteger.valueOf(role),
                    BigInteger.valueOf(department));
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
        }
    }

    public void removeUser(String userAddress) {
        try {
            pipService.removeUser(userAddress);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
        }
    }

    public List<BaseUserEntity> getAllUsers() {
        List<PIP.BaseUser> usersFromBC = null;
        try {
            usersFromBC = pipService.getAllUsers();
            if (Boolean.FALSE.equals(CollectionUtils.isEmpty(usersFromBC))) {
                return usersFromBC.stream()
                        .map(baseUserConverter::convert).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
