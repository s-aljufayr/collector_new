package tech.wakeb.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.wakeb.collector.model.Permission;
import tech.wakeb.collector.model.User;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
