package com.cryptogate.service;

import com.cryptogate.contract.PIP;
import com.cryptogate.contract.service.PIPService;
import com.cryptogate.converters.SourceConverter;
import com.cryptogate.dto.Source;
import com.cryptogate.entity.SourceServiceAuditEntity;
import com.cryptogate.enums.OperationType;
import com.cryptogate.enums.TransactionStatus;
import com.cryptogate.repository.SourceServiceAuditRepository;
import com.cryptogate.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class SourceService {

    private final PIPService pipService;

    private final SourceConverter sourceConverter;

    private final SourceServiceAuditRepository auditRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void addSource(String owner, String title,
                          Long sourceType, Long secretLevel,
                          String allowedUsers) {
        TransactionReceipt receipt;
        UUID sourceId = UUID.randomUUID();
        SourceServiceAuditEntity sourceServiceAuditEntity = new SourceServiceAuditEntity();
        sourceServiceAuditEntity.setSourceUuid(sourceId);
        sourceServiceAuditEntity.setOperationType(OperationType.CREATE);
        try {
            TransactionReceipt transaction = pipService.addSource(sourceId.toString(), title, owner,
                    BigInteger.valueOf(sourceType),
                    BigInteger.valueOf(secretLevel),
                    StringUtils.isEmpty(allowedUsers) ? CommonConstants.NULL_USER : allowedUsers);
            sourceServiceAuditEntity.setTransaction(objectMapper.writeValueAsString(transaction));
            sourceServiceAuditEntity.setStatus(TransactionStatus.SUCCESS);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
            sourceServiceAuditEntity.setStatus(TransactionStatus.FAILED);
            sourceServiceAuditEntity.setErrorDesc(e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
            sourceServiceAuditEntity.setErrorDesc("Техническая ошибка");
            sourceServiceAuditEntity.setStatus(TransactionStatus.FAILED);
        }
        auditRepository.save(sourceServiceAuditEntity);
    }

    public void removeSource(String sourceId) {
        TransactionReceipt receipt;
        SourceServiceAuditEntity sourceServiceAuditEntity = new SourceServiceAuditEntity();
        sourceServiceAuditEntity.setSourceUuid(UUID.fromString(sourceId));
        sourceServiceAuditEntity.setOperationType(OperationType.DELETE);
        try {
            TransactionReceipt transaction = pipService.removeSource(sourceId);
            sourceServiceAuditEntity.setTransaction(objectMapper.writeValueAsString(transaction));
            sourceServiceAuditEntity.setStatus(TransactionStatus.SUCCESS);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
            sourceServiceAuditEntity.setStatus(TransactionStatus.FAILED);
            sourceServiceAuditEntity.setErrorDesc(e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
            sourceServiceAuditEntity.setErrorDesc("Техническая ошибка");
            sourceServiceAuditEntity.setStatus(TransactionStatus.FAILED);
        }
        auditRepository.save(sourceServiceAuditEntity);
    }

    public List<Source> getAllSources() {
        List<PIP.Source> sourcesFromBC = null;
        try {
            sourcesFromBC = pipService.getAllSources();
            if (Boolean.FALSE.equals(CollectionUtils.isEmpty(sourcesFromBC))) {
                return sourcesFromBC.stream()
                        .map(sourceConverter::convert).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.info("Exception reason: {}", e.getMessage());
        }
        return new ArrayList<>();
    }
}
