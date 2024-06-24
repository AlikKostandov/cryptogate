package com.cryptogate.service;

import com.cryptogate.contract.service.PEPService;
import com.cryptogate.entity.AccessRequestAuditEntity;
import com.cryptogate.repository.AccessRequestAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final PEPService pepService;

    private final AccessRequestAuditRepository auditRepository;

    public boolean checkAccess(String userAddress, String sourceId) {
        boolean isAccsessGranted = false;
        AccessRequestAuditEntity accessRequest = new AccessRequestAuditEntity();
        accessRequest.setInitiatorAddress(userAddress);
        accessRequest.setSourceUuid(UUID.fromString(sourceId));
        try {
            isAccsessGranted = pepService.checkAccess(userAddress, sourceId);
        } catch (Exception e) {
            log.info("Exception reason: {}", e.getMessage());
            accessRequest.setErrorDesc(e.getMessage());
        }
        accessRequest.setAccessGranted(isAccsessGranted);
        auditRepository.save(accessRequest);
        return isAccsessGranted;
    }
}
