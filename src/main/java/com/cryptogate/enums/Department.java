package com.cryptogate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Department {

    IT(1L, "Информационные технологии"),
    HR(2L, "Отдел кадров"),
    FINANCE(3L, "Финансы"),
    SALES(4L, "Отдел продаж"),
    MARKETING(5L, "Маркетинг"),
    OPERATIONS(6L, "Операции"),
    CUSTOMER_SERVICE(7L, "Служба поддержки");

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

