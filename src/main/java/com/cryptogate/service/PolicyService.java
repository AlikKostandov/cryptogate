package com.cryptogate.service;

import com.cryptogate.contract.PAP;
import com.cryptogate.contract.service.PAPService;
import com.cryptogate.converters.PolicyConverter;
import com.cryptogate.dto.Policy;
import com.cryptogate.entity.PolicyServiceAuditEntity;
import com.cryptogate.enums.OperationType;
import com.cryptogate.enums.PolicyCategory;
import com.cryptogate.enums.SourceType;
import com.cryptogate.enums.TransactionStatus;
import com.cryptogate.repository.PolicyServiceAuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PolicyService {

    private final PAPService papService;

    private final PolicyConverter policyConverter;

    private final PolicyServiceAuditRepository auditRepository;

    private final ObjectWriter writer = (new ObjectMapper()).writerWithDefaultPrettyPrinter();

    public void addPolicy(String sourceId, Long sourceType,
                          Long allowedRole, Long allowedDepartment) {
        TransactionReceipt transaction;
        UUID policyId = UUID.randomUUID();
        BigInteger sType = define(sourceType);
        PolicyServiceAuditEntity auditEntity = new PolicyServiceAuditEntity();
        auditEntity.setOperationType(OperationType.CREATE);
        auditEntity.setPolicyUuid(policyId);
        if (sType.longValue() == 0L) {
            auditEntity.setPolicyCategory(PolicyCategory.SOURCE_ID);
            auditEntity.setSourceUuid(UUID.fromString(sourceId));
        } else {
            auditEntity.setPolicyCategory(PolicyCategory.SOURCE_TYPE);
            auditEntity.setSourceType(SourceType.getById(sourceType));
        }
        try {
            transaction = papService.addPolicy(policyId.toString(),
                    defineSourceId(sourceId), sType,
                    define(allowedRole),
                    define(allowedDepartment));
            auditEntity.setTransaction(writer.writeValueAsString(transaction));
            auditEntity.setStatus(TransactionStatus.SUCCESS);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
            auditEntity.setStatus(TransactionStatus.FAILED);
            auditEntity.setErrorDesc(e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
            auditEntity.setErrorDesc("Техническая ошибка");
            auditEntity.setStatus(TransactionStatus.FAILED);
        }
        auditRepository.save(auditEntity);
    }

    public void removePolicy(String policyId) {
        TransactionReceipt transaction;
        PolicyServiceAuditEntity auditEntity = new PolicyServiceAuditEntity();
        auditEntity.setOperationType(OperationType.DELETE);
        auditEntity.setPolicyUuid(UUID.fromString(policyId));
        try {
            transaction = papService.removePolicy(policyId);
            auditEntity.setTransaction(writer.writeValueAsString(transaction));
            auditEntity.setStatus(TransactionStatus.SUCCESS);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
            auditEntity.setStatus(TransactionStatus.FAILED);
            auditEntity.setErrorDesc(e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
            auditEntity.setErrorDesc("Техническая ошибка");
            auditEntity.setStatus(TransactionStatus.FAILED);
        }
        auditRepository.save(auditEntity);
    }

    public List<Policy> getAllPolicy() {
        List<PAP.Policy> policiesFromBC = null;
        try {
            policiesFromBC = papService.getAllPolicy();
            if (Boolean.FALSE.equals(CollectionUtils.isEmpty(policiesFromBC))) {
                return policiesFromBC.stream()
                        .map(policyConverter::convert).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    private static @NotNull BigInteger define(Long allowedRole) {
        return allowedRole != null ? BigInteger.valueOf(allowedRole) : BigInteger.valueOf(0L);
    }

    private static @NotNull String defineSourceId(String sourceId) {
        return StringUtils.isEmpty(sourceId) ? "" : sourceId;
    }

}

