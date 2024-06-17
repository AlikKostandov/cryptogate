package com.cryptogate.dto;

import com.cryptogate.enums.SecretLevel;
import com.cryptogate.enums.SourceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Source implements Convertable{

    private String sourceId;

    private String title;

    private String owner;

    private SourceType sourceType;

    private SecretLevel secretLevel;
}
