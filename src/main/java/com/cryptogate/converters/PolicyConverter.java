package com.cryptogate.converters;

import com.cryptogate.contract.PAP;
import com.cryptogate.dto.Policy;
import com.cryptogate.enums.Department;
import com.cryptogate.enums.Role;
import com.cryptogate.enums.SourceType;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class PolicyConverter {

    public Policy convert(PAP.Policy entity) {
        Role role = getRole(entity);
        SourceType sourceType = getSourceType(entity);
        Department department = getDepartment(entity);
        return Policy.builder()
                .policyId(entity.policyId)
                .sourceId(entity.sourceId)
                .sourceType(sourceType)
                .allowedRole(role)
                .allowedDepartment(department)
                .build();
    }

    private static @Nullable Department getDepartment(PAP.Policy entity) {
        return entity.allowedDepartment.longValue() == 0 ? null : Department.getByDepId(entity.allowedDepartment.longValue());
    }

    private static @Nullable Role getRole(PAP.Policy entity) {
        return entity.allowedRole.longValue() == 0 ? null : Role.getById(entity.allowedRole.longValue());
    }

    private static @Nullable SourceType getSourceType(PAP.Policy entity) {
        return entity.sourceType.longValue() == 0 ? null : SourceType.getById(entity.sourceType.longValue());
    }
}
