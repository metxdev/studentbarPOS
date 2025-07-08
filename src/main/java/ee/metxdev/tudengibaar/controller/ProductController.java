package ee.metxdev.tudengibaar.controller;

import ee.metxdev.tudengibaar.DTO.ProductDto;
import ee.metxdev.tudengibaar.entity.Category;
import ee.metxdev.tudengibaar.entity.Product;
import ee.metxdev.tudengibaar.repository.ProductSalesSummary;
import ee.metxdev.tudengibaar.service.OrderService;
import ee.metxdev.tudengibaar.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final OrderService orderService;

    /** List all products */
    @GetMapping
    public List<Product> list() {
        return productService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody ProductDto body) {
        return productService.save(body);
    }


    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDto dto) {
        return productService.update(id ,dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }


}
