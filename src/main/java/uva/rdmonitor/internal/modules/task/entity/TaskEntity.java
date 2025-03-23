package uva.rdmonitor.internal.modules.task.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uva.rdmonitor.internal.modules.task.entity.enumeration.TaskStateEnum;
import uva.rdmonitor.internal.modules.taskList.entity.TaskListEntity;
import uva.rdmonitor.internal.shared.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "TASK")
public class TaskEntity extends BaseEntity {

    private String title;
    private String description;

    private LocalDateTime completedTime;

    @Enumerated(EnumType.STRING)
    private TaskStateEnum state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_LIST_ID")
    private TaskListEntity taskList;
}