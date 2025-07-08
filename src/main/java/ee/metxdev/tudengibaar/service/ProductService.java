package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.DTO.ProductDto;
import ee.metxdev.tudengibaar.entity.Category;
import ee.metxdev.tudengibaar.entity.Product;
import ee.metxdev.tudengibaar.repository.CategoryRepository;
import ee.metxdev.tudengibaar.repository.ProductRepository;
import exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepo;

    public List<Product> findAll() {
       return productRepository.findAll();
    }

    public Product save(ProductDto dto) {
        if (productRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Product already exists");
        }


        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product update(Long id, ProductDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));


        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setCategory(category);

        return productRepository.save(existing);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

}
