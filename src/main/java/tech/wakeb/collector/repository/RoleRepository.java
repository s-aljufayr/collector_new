package tech.wakeb.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.wakeb.collector.model.Role;
import tech.wakeb.collector.model.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
