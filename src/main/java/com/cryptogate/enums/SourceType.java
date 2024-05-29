package com.cryptogate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SourceType {

    HTML(0L, ".html"),
    TXT(1L, ".txt"),
    PDF(2L, ".pdf"),
    XLSX(3L, ".xlsx"),
    DOCX(4L, ".docx"),
    ZIP(5L, ".zip"),
    PNG(6L, ".png"),
    JPEG(7L, ".jpeg");

    private final Long id;

    private final String extension;

    SourceType(Long id, String extension) {
        this.id = id;
        this.extension = extension;
    }

    public static SourceType getById(Long id) {
        return Arrays.stream(SourceType.values())
                .filter(type -> type.id.equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown extension with id: " + id));
    }
}
