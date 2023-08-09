package tech.wakeb.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.wakeb.collector.model.Activity;
import tech.wakeb.collector.model.User;

public interface ActivityRepository extends JpaRepository<Activity, Long> {



}
