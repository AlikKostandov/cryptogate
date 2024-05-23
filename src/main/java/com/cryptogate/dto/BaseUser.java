package com.cryptogate.dto;

import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class BaseUser {

    private String userAddress;

    private String username;

    private String password;

    private Role role;

    private Department department;

}
