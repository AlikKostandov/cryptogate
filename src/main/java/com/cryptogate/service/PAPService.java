package com.cryptogate.service;

import com.cryptogate.contract.PAP;
import com.cryptogate.contract.PRP;
import com.cryptogate.converters.PolicyConverter;
import com.cryptogate.dto.Policy;
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
public class PAPService {

    private final PAP pap;

    private final PRP prp;

    private final PolicyConverter policyConverter;

    public void addPolicy(String sourceId, Long sourceType,
                          Long allowedRole, Long allowedDepartment) throws Exception {
        try {
            String policyId = UUID.randomUUID().toString();
            prp.storePolicy(policyId,
                    StringUtils.isEmpty(sourceId) ? "" : sourceId,
                    sourceType != null ? BigInteger.valueOf(sourceType) : BigInteger.valueOf(0L),
                    allowedRole != null ? BigInteger.valueOf(allowedRole) : BigInteger.valueOf(0L),
                    allowedDepartment != null ? BigInteger.valueOf(allowedDepartment) : BigInteger.valueOf(0L)).send();
        } catch (TransactionException e) {
            log.info("Exception reason: " + e.getMessage());
        }
    }

    public void removePolicy(String policyId) {
        try {
            prp.removePolicy(policyId).send();
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
        }
    }

    public List<Policy> getAllPolicy() {
        List<PAP.Policy> policiesFromBC = null;
        try {
            policiesFromBC = pap.getAllPolicies().send();
            if (Boolean.FALSE.equals(CollectionUtils.isEmpty(policiesFromBC))) {
                return policiesFromBC.stream()
                        .map(policyConverter::convert).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.info("Exception reason: " + e.getMessage());
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

}

