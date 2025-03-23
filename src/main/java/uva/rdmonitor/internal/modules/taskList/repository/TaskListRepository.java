package uva.rdmonitor.internal.modules.taskList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uva.rdmonitor.internal.modules.taskList.entity.TaskListEntity;

@Repository
public interface TaskListRepository extends JpaRepository<TaskListEntity, Long> {
}