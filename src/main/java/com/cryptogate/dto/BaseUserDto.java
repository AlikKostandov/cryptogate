package com.cryptogate.dto;

import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserDto {

    private String userAddress;

    private String username;

    private Role role;

    private Department department;
}
