package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.DTO.CreateProductDTO;
import ee.metxdev.tudengibaar.DTO.ProductDTO;
import ee.metxdev.tudengibaar.DTO.UpdateProductDTO;
import ee.metxdev.tudengibaar.entity.Category;
import ee.metxdev.tudengibaar.entity.Product;
import ee.metxdev.tudengibaar.mappers.ProductMapper;
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

    public List<ProductDTO> findAll() {
       return productRepository.findAll()
               .stream()
               .map(ProductMapper::toDTO)
               .toList();
    }

    public ProductDTO save(CreateProductDTO dto) {
        if (productRepository.findByNameAndCategory_Id(dto.getName(), dto.getCategoryId()).isPresent()) {
            throw new IllegalArgumentException("Product already exists");
        }


        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Product product = ProductMapper.toEntity(dto);
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    public ProductDTO update(Long id, UpdateProductDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setCategory(category);

        Product saved = productRepository.save(existing);
        return ProductMapper.toDTO(saved);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

}
