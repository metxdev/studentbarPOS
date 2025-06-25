package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.entity.Category;
import ee.metxdev.tudengibaar.repository.CategoryRepository;
import exception.DuplicateCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category body) {
        if (body == null) return null;

        if (categoryRepository.findByName(body.getName()).isPresent()) {
            throw new IllegalArgumentException("Category already exists");
        }
        return categoryRepository.save(body);
    }

}
