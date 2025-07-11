package ee.metxdev.tudengibaar.controller;

import ee.metxdev.tudengibaar.DTO.CategoryDTO;
import ee.metxdev.tudengibaar.entity.Category;
import ee.metxdev.tudengibaar.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> list() {
        return categoryService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category body) {
        return categoryService.save(body);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

}
