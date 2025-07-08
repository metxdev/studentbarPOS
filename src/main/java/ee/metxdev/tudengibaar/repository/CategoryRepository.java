package ee.metxdev.tudengibaar.repository;

import ee.metxdev.tudengibaar.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    List<Category> id(Long id);
}
