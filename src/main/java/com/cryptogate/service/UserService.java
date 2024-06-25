package com.cryptogate.service;

import com.cryptogate.contract.PIP;
import com.cryptogate.contract.service.PIPService;
import com.cryptogate.converters.BaseUserConverter;
import com.cryptogate.dto.BaseUserDto;
import com.cryptogate.entity.UserServiceAuditEntity;
import com.cryptogate.enums.OperationType;
import com.cryptogate.enums.TransactionStatus;
import com.cryptogate.repository.UserServiceAuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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

    private final UserServiceAuditRepository auditRepository;

    private final ObjectWriter writer = (new ObjectMapper()).writerWithDefaultPrettyPrinter();

    public void registerUser(String userAddress, String username,
                             Long role, Long department) {
        TransactionReceipt transaction;
        UserServiceAuditEntity entity = new UserServiceAuditEntity();
        entity.setUserAddress(userAddress);
        entity.setOperationType(OperationType.CREATE);
        try {
            transaction = pipService.addUser(
                    userAddress, username,
                    BigInteger.valueOf(role),
                    BigInteger.valueOf(department));
            entity.setTransaction(writer.writeValueAsString(transaction));
            entity.setStatus(TransactionStatus.SUCCESS);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
            entity.setErrorDesc(e.getMessage());
            entity.setStatus(TransactionStatus.FAILED);
        } catch (Exception e) {
            log.info("Техническая ошибка");
            entity.setErrorDesc("Техническая ошибка");
            entity.setStatus(TransactionStatus.FAILED);
        }
        auditRepository.save(entity);
    }

    public void removeUser(String userAddress) {
        UserServiceAuditEntity entity = new UserServiceAuditEntity();
        entity.setUserAddress(userAddress);
        entity.setOperationType(OperationType.DELETE);
        try {
            TransactionReceipt transaction = pipService.removeUser(userAddress);
            entity.setTransaction(writer.writeValueAsString(transaction));
            entity.setStatus(TransactionStatus.SUCCESS);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
            entity.setErrorDesc(e.getMessage());
            entity.setStatus(TransactionStatus.FAILED);
        } catch (Exception e) {
            log.info("Техническая ошибка");
            entity.setErrorDesc("Техническая ошибка");
            entity.setStatus(TransactionStatus.FAILED);
        }
        auditRepository.save(entity);
    }

    public List<BaseUserDto> getAllUsers() {
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
