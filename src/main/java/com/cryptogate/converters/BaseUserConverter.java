package com.cryptogate.converters;

import com.cryptogate.contract.PIP;
import com.cryptogate.dto.BaseUserDto;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class BaseUserConverter {

    public BaseUserDto convert(PIP.BaseUser entity) {
        return BaseUserDto.builder()
                .userAddress(entity.userAddress)
                .username(entity.username)
                .role(Role.getById(entity.role.longValue()))
                .department(Department.getByDepId(entity.department.longValue()))
                .build();
    }
}
