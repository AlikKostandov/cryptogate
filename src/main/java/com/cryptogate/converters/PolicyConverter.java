package com.cryptogate.converters;

import com.cryptogate.contract.PAP;
import com.cryptogate.dto.Policy;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import com.cryptogate.enums.SourceType;
import org.springframework.stereotype.Service;

@Service
public class PolicyConverter {

    public Policy convert(PAP.Policy entity) {
        SourceType sourceType = entity.sourceType.longValue() == 0 ? null : SourceType.getById(entity.sourceType.longValue());
        Role role = entity.allowedRole.longValue() == 0 ? null : Role.getById(entity.allowedRole.longValue());
        Department department = entity.allowedDepartment.longValue() == 0 ? null : Department.getByDepId(entity.allowedDepartment.longValue());
        return Policy.builder()
                .policyId(entity.policyId)
                .sourceId(entity.sourceId)
                .sourceType(sourceType)
                .allowedRole(role)
                .allowedDepartment(department)
                .build();
    }
}
