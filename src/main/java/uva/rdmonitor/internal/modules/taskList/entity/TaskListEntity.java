package uva.rdmonitor.internal.modules.taskList.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uva.rdmonitor.internal.modules.task.entity.TaskEntity;
import uva.rdmonitor.internal.modules.taskList.entity.enumeration.TaskListStateEnum;
import uva.rdmonitor.internal.shared.entity.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TASK_LIST")
public class TaskListEntity extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskListStateEnum state;

    @OneToMany(mappedBy = "taskList", fetch = FetchType.LAZY)
    private List<TaskEntity> tasks;
}