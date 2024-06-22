package com.cryptogate.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SourceType {

    HTML(1L, ".html"),
    TXT(2L, ".txt"),
    PDF(3L, ".pdf"),
    XLSX(4L, ".xlsx"),
    DOCX(5L, ".docx"),
    ZIP(6L, ".zip"),
    PNG(7L, ".png"),
    JPEG(8L, ".jpeg");

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
