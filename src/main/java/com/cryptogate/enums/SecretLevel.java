package com.cryptogate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SecretLevel {

    PUBLIC(1L),
    ARBITRARY(2L),
    CONFIDENTIAL(3L),
    PRIVATE(4L);

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
