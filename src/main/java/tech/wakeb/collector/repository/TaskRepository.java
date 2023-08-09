package tech.wakeb.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.wakeb.collector.model.Task;
import tech.wakeb.collector.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
