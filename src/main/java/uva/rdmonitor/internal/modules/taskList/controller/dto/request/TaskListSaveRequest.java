package uva.rdmonitor.internal.modules.taskList.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uva.rdmonitor.internal.modules.task.controller.dto.request.TaskSaveRequest;
import uva.rdmonitor.internal.shared.interfaces.UpdateRequest;

import java.util.List;

@Getter
@Setter
public class TaskListSaveRequest {

    @NotNull(groups = {UpdateRequest.class})
    private Long id;

    @NotNull(groups = {UpdateRequest.class})
    private Long version;

    @NotNull
    private String name;

    @Valid
    private List<TaskSaveRequest> tasks;
}
