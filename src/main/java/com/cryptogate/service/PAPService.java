package com.cryptogate.service;

import com.cryptogate.contract.PAP;
import com.cryptogate.dto.BaseUser;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class PAPService {

    private final PAP pap;

    public TransactionReceipt registerUser(String userAddress, BaseUser user) throws Exception {
        log.info("Попытка зарегистрировать пользователя с userAddress = {}", userAddress);
        return pap.registerUser(
                userAddress,
                user.getUsername(),
                user.getPassword(),
                BigInteger.valueOf(user.getRole().getId()),
                BigInteger.valueOf(user.getDepartment().getDepId()))
                .send();
    }

    public BaseUser getUser(String userAddress) throws Exception {
        Tuple3<String, BigInteger, BigInteger> userData = pap.getUser(userAddress).send();
        return BaseUser.builder()
                .username(userData.getValue1())
                .role(Role.getById((long) userData.getValue2().intValue()))
                .department(Department.getByDepId((long) userData.getValue3().intValue())).build();
    }

    public boolean authenticateUser(String userAddress, String password) throws Exception {
        log.info("Попытка авторизации пользователя с userAddress = {}", userAddress);
        return pap.authenticateUser(userAddress, password).send();
    }
}
