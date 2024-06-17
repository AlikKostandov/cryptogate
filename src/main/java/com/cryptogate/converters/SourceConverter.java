package com.cryptogate.converters;

import com.cryptogate.contract.PIP;
import com.cryptogate.dto.Source;
import com.cryptogate.enums.SecretLevel;
import com.cryptogate.enums.SourceType;
import org.springframework.stereotype.Component;

@Component("sourceConverter")
public class SourceConverter implements BaseConverter<PIP.Source> {

    @Override
    public Source convert(PIP.Source entity) {
        return Source.builder()
                .sourceId(entity.sourceId)
                .owner(entity.owner)
                .title(entity.title)
                .sourceType(SourceType.getById(entity.sourceType.longValue()))
                .secretLevel(SecretLevel.getByLevel(entity.secretLevel.longValue()))
                .build();
    }
}
