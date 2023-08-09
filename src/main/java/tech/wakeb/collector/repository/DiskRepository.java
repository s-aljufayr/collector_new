package tech.wakeb.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.wakeb.collector.model.Disk;
import tech.wakeb.collector.model.User;

public interface DiskRepository extends JpaRepository<Disk, Long> {
}
