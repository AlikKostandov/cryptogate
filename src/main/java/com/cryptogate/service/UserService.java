package com.cryptogate.service;

import com.cryptogate.dto.BaseUser;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
@RequiredArgsConstructor
public class UserService {

    private PAPService papService;

    private void registerUser(String username, Long roleId, Long depId) throws Exception {
        BaseUser user = new BaseUser()
                .builder()
                .username(username)
                .role(Role.getById(roleId))
                .department(Department.getByDepId(depId))
                .build();
        TransactionReceipt transactionReceipt = papService.registerUser(user);
    }
}
