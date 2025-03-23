package uva.rdmonitor.internal.modules.taskList.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uva.rdmonitor.internal.modules.task.service.TaskService;
import uva.rdmonitor.internal.modules.taskList.controller.dto.TaskListDto;
import uva.rdmonitor.internal.modules.taskList.controller.dto.request.TaskListSaveRequest;
import uva.rdmonitor.internal.modules.taskList.entity.TaskListEntity;
import uva.rdmonitor.internal.modules.taskList.entity.enumeration.TaskListStateEnum;
import uva.rdmonitor.internal.modules.taskList.repository.TaskListRepository;
import uva.rdmonitor.internal.shared.dto.EntitySaveResponse;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskListService {

    private final TaskListRepository taskListRepository;
    private final TaskService taskService;

    @Transactional
    public EntitySaveResponse save(Long id, @Valid TaskListSaveRequest saveRequest) {
        var taskList = resolveToEntity(id);
        resolveEditableFields(saveRequest, taskList);

        return EntitySaveResponse.of(taskListRepository.saveAndFlush(taskList));
    }

    public TaskListDto getTaskListById(@NonNull Long id) {
        var taskList = taskListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TaskList with id " + id + " was not found"));

        return TaskListDto.of(taskList);
    }

    private void resolveEditableFields(@NonNull TaskListSaveRequest saveRequest, @NonNull TaskListEntity taskList) {
        taskList.setName(saveRequest.getName());
        if (saveRequest.getTasks() != null) {
            taskList.setTasks(taskService.saveForList(saveRequest.getTasks(), taskList));
        }
    }

    private TaskListEntity resolveToEntity(Long id) {
        if (id == null) {
            var newEntity = new TaskListEntity();
            newEntity.setState(TaskListStateEnum.CREATED);
            return newEntity;
        }

        return taskListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TaskList with id " + id + " was not found"));
    }

}