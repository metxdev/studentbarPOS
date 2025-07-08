package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.DTO.CategoryDto;
import ee.metxdev.tudengibaar.entity.Category;
import ee.metxdev.tudengibaar.repository.CategoryRepository;
import exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

    public Category update(Long id, CategoryDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        category.setName(dto.getName());
        return category;
    }

    public Category delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.findById(id);
        return category;
    }

}
