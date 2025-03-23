package uva.rdmonitor.internal.modules.taskList.controller.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uva.rdmonitor.internal.modules.task.controller.dto.TaskDto;
import uva.rdmonitor.internal.modules.taskList.entity.TaskListEntity;
import uva.rdmonitor.internal.shared.dto.BaseEntityDto;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class TaskListDto extends BaseEntityDto {
    private String name;
    private List<TaskDto> tasks;

    public static TaskListDto of(@NonNull TaskListEntity entity) {
        return TaskListDto.builder()
                .baseEntityDto(entity)
                .name(entity.getName())
                .tasks(entity.getTasks() != null ?
                        entity.getTasks()
                        .stream()
                        .map(TaskDto::of)
                        .toList() : null
                )
                .build();
    }
}