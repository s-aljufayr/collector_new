package tech.wakeb.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.wakeb.collector.model.Category;
import tech.wakeb.collector.model.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
