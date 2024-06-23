package com.cryptogate.service;

import com.cryptogate.contract.service.PEPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.exceptions.TransactionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final PEPService pepService;

    public boolean checkAccess(String userAddress, String sourceId) {
        try {
            return pepService.checkAccess(userAddress, sourceId);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
        }
        return false;
    }
}
