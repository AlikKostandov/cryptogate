package com.cryptogate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Department {

    IT(0L, "Информационные технологии"),
    HR(1L, "Отдел кадров"),
    FINANCE(2L, "Финансы"),
    SALES(3L, "Отдел продаж"),
    MARKETING(4L, "Маркетинг"),
    OPERATIONS(5L, "Операции"),
    CUSTOMER_SERVICE(6L, "Служба поддержки");

    private final Long depId;
    private final String departmentName;

    Department(Long depId, String departmentName) {
        this.depId = depId;
        this.departmentName = departmentName;
    }

    public static Department getByDepId(Long depId) {
        return Arrays.stream(Department.values())
                .filter(department -> department.depId.equals(depId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found department for id: " + depId));
    }

}

