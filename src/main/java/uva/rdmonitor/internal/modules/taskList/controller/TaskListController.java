package uva.rdmonitor.internal.modules.taskList.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uva.rdmonitor.internal.modules.taskList.controller.dto.TaskListDto;
import uva.rdmonitor.internal.modules.taskList.controller.dto.request.TaskListSaveRequest;
import uva.rdmonitor.internal.modules.taskList.service.TaskListService;
import uva.rdmonitor.internal.shared.dto.EntitySaveResponse;
import uva.rdmonitor.internal.shared.interfaces.CreateRequest;
import uva.rdmonitor.internal.shared.interfaces.UpdateRequest;
import uva.rdmonitor.internal.shared.utils.RestUtils;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(RestUtils.INTERNAL_PATH + "/task-list")
public class TaskListController {

    private final TaskListService taskListService;

    @PostMapping
    @Validated(CreateRequest.class)
    @PreAuthorize("hasRole('MODERATOR')")
    public EntitySaveResponse create(@Valid @RequestBody TaskListSaveRequest saveRequest) {
        return taskListService.save(null, saveRequest);
    }

    @PostMapping("{id}")
    @Validated(UpdateRequest.class)
    @PreAuthorize("hasRole('MODERATOR')")
    public EntitySaveResponse update(@PathVariable Long id, @Valid @RequestBody TaskListSaveRequest saveRequest) {
          return taskListService.save(id, saveRequest);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('DEVELOPER')")
    public TaskListDto getTaskListById(@PathVariable Long id) {
        return taskListService.getTaskListById(id);
    }
}
