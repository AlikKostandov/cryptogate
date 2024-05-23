package com.cryptogate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {

    ADMIN(0L, "Админ"),
    MANAGER(1L, "Менеджер"),
    EMPLOYEE(2L, "Сотрудник"),
    GUEST(3L, "Гость");

    private final Long id;

    private final String roleName;

    Role(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public static Role getById(Long byId) {
        return Arrays.stream(Role.values())
                .filter(role -> role.id.equals(byId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found role for id: " + byId));
    }

}
