package com.cryptogate.service;

import com.cryptogate.contract.PIP;
import com.cryptogate.dto.BaseUser;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tuples.generated.Tuple2;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class PIPService {

    private final PIP pip;

    public TransactionReceipt setUserAttributes(String userAddress, BigInteger role, BigInteger department) throws Exception {
        return pip.setUserAttributes(userAddress, role, department).send();
    }

    public BaseUser getUserAttributes(String userAddress) throws Exception {
        Tuple2<BigInteger, BigInteger> userAttributes = pip.getUserAttributes(userAddress).send();
        return BaseUser.builder()
                .role(Role.getById((long) userAttributes.getValue1().intValue()))
                .department(Department.getByDepId((long) userAttributes.getValue2().intValue()))
                .build();
    }

    public void setObjectAttributes(String objectCID, BigInteger type, BigInteger secretLevel) throws Exception {
        pip.setObjectAttributes(objectCID.getBytes(StandardCharsets.UTF_8), type, secretLevel).send();
    }

    public Tuple2<BigInteger, BigInteger> getObjectAttributes(String objectCID) throws Exception {
        return pip.getObjectAttributes(objectCID.getBytes(StandardCharsets.UTF_8)).send();
    }

}

