package com.cryptogate.contract.service;

import com.cryptogate.contract.PAP;
import com.cryptogate.contract.PRP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PAPService {

    private final PAP pap;

    private final PRP prp;

    public void addPolicy(String policyId, String sourceId, BigInteger sourceType,
                          BigInteger allowedRole, BigInteger allowedDepartment) throws Exception {
        prp.storePolicy(policyId, sourceId, sourceType, allowedRole, allowedDepartment).send();
    }

    public void removePolicy(String policyId) throws Exception {
        prp.removePolicy(policyId).send();
    }

    public List<PAP.Policy> getAllPolicy() throws Exception {
        return pap.getAllPolicies().send();
    }
}

