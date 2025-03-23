package uva.rdmonitor.internal.modules.task.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uva.rdmonitor.internal.modules.task.controller.dto.request.TaskSaveRequest;
import uva.rdmonitor.internal.modules.task.entity.TaskEntity;
import uva.rdmonitor.internal.modules.task.entity.enumeration.TaskStateEnum;
import uva.rdmonitor.internal.modules.task.repository.TaskRepository;
import uva.rdmonitor.internal.modules.taskList.entity.TaskListEntity;
import uva.rdmonitor.internal.modules.taskList.entity.enumeration.TaskListStateEnum;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public List<TaskEntity> saveForList(@Valid List<TaskSaveRequest> saveRequests, @NonNull TaskListEntity taskListEntity) {

        List<TaskEntity> tasks = new java.util.ArrayList<>(Collections.emptyList());

        for (var request : saveRequests) {
            var task = resolveToEntity(request.getId());
            resolveEditableFields(request, task);
            task.setTaskList(taskListEntity);

            tasks.add(task);
        }

        return taskRepository.saveAll(tasks);
    }

    private void resolveEditableFields(@NonNull TaskSaveRequest saveRequest, @NonNull TaskEntity task) {
        task.setTitle(saveRequest.getTitle());
        task.setDescription(saveRequest.getDescription());
    }

    private TaskEntity resolveToEntity(Long id) {
        if (id == null) {
            var newTask = new TaskEntity();
            newTask.setState(TaskStateEnum.CREATED);
            return newTask;
        }

        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

}
