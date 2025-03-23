package uva.rdmonitor.internal.modules.task.controller.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uva.rdmonitor.internal.shared.interfaces.UpdateRequest;

@Getter
@Setter
public class TaskSaveRequest {

    @NotNull(groups = {UpdateRequest.class})
    private Long id;

    @NotNull(groups = {UpdateRequest.class})
    private Long version;

    @NotNull
    private String title;

    @Valid
    private String description;
}
