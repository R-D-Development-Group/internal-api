package uva.rdmonitor.internal.shared.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import uva.rdmonitor.internal.shared.entity.BaseEntity;

@Getter
@SuperBuilder
@RequiredArgsConstructor
public class EntitySaveResponse {
    private final Long id;
    private final Long version;

    public static <T extends BaseEntity> EntitySaveResponse of(T savedEntity) {
        return EntitySaveResponse.builder()
                .id(savedEntity.getId())
                .version(savedEntity.getVersion())
                .build();
    }
}
