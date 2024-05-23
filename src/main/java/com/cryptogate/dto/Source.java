package com.cryptogate.dto;

import com.cryptogate.enums.SecretLevel;
import com.cryptogate.enums.SourceType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Source {

    private String sourceCID;

    private SourceType type;

    private String owner;

    private SecretLevel secretLevel;

    private List<String> sourceTags;
}
