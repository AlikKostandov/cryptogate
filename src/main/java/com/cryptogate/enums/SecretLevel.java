package com.cryptogate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SecretLevel {

    PUBLIC(0L),
    ARBITRARY(1L),
    CONFIDENTIAL(2L),
    PRIVATE(3L);

    private final Long level;

    SecretLevel(Long level) {
        this.level = level;
    }

    public static SecretLevel getByLevel(Long level) {
        return Arrays.stream(SecretLevel.values())
                .filter(sl -> sl.level.equals(level))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found secret level: " + level));
    }

}
