package com.cryptogate.dto;

import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import com.cryptogate.enums.SourceType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Policy {

    private String policyId;

    private String sourceId;

    private SourceType sourceType;

    private Role allowedRole;

    private Department allowedDepartment;

}
