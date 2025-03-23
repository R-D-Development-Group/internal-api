package uva.rdmonitor.internal.shared.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import uva.rdmonitor.internal.shared.interfaces.GenericDtoProvider;

@Data
@SuperBuilder
public class BaseEntityDto {
    private final Long id;
    private final Long version;

    public abstract static class BaseEntityDtoBuilder<C extends BaseEntityDto, B extends BaseEntityDtoBuilder<C, B>> {
        public B baseEntityDto(GenericDtoProvider genericDtoDataProvider) {
            this.id = genericDtoDataProvider.getId();
            this.version = genericDtoDataProvider.getVersion();
                        return this.self();
        }
    }
}