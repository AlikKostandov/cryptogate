package com.cryptogate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {

    ADMIN(1L, "Админ"),
    MANAGER(2L, "Менеджер"),
    EMPLOYEE(3L, "Сотрудник"),
    GUEST(4L, "Гость");

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
