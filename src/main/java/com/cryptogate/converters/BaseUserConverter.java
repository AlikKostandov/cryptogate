package com.cryptogate.converters;

import com.cryptogate.contract.PIP;
import com.cryptogate.dto.BaseUserEntity;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class BaseUserConverter {

    public BaseUserEntity convert(PIP.BaseUser entity) {
        return BaseUserEntity.builder()
                .userAddress(entity.userAddress)
                .username(entity.username)
                .role(Role.getById(entity.role.longValue()))
                .department(Department.getByDepId(entity.department.longValue()))
                .build();
    }
}
