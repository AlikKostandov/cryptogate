package com.cryptogate.service;

import com.cryptogate.contract.PIP;
import com.cryptogate.contract.service.PIPService;
import com.cryptogate.converters.SourceConverter;
import com.cryptogate.dto.Source;
import com.cryptogate.util.CommonConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
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

    public void addSource(String owner, String title,
                          Long sourceType, Long secretLevel,
                          String allowedUsers) {
        try {
            String sourceId = UUID.randomUUID().toString();
            pipService.addSource(sourceId, title, owner,
                    BigInteger.valueOf(sourceType),
                    BigInteger.valueOf(secretLevel),
                    StringUtils.isEmpty(allowedUsers) ? CommonConstants.NULL_USER : allowedUsers);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
        }
    }

    public void removeSource(String sourceId) {
        try {
            pipService.removeSource(sourceId);
        } catch (TransactionException e) {
            log.info("Exception reason: {}", e.getMessage());
        } catch (Exception e) {
            log.info("Техническая ошибка");
        }
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
