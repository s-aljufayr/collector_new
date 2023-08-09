package tech.wakeb.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.wakeb.collector.model.Device;
import tech.wakeb.collector.model.User;

public interface DeviceRepository extends JpaRepository<Device, Long> {



}
