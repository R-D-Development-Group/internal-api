package uva.rdmonitor.internal.modules.task.controller.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uva.rdmonitor.internal.modules.task.entity.TaskEntity;
import uva.rdmonitor.internal.shared.dto.BaseEntityDto;

@Getter
@Setter
@SuperBuilder
public class TaskDto extends BaseEntityDto {
    private String title;
    private String description;

    public static TaskDto of(@NonNull TaskEntity entity) {
        return TaskDto.builder()
                .baseEntityDto(entity)
                .title(entity.getTitle())
                .description(entity.getDescription())
                .build();
    }
}