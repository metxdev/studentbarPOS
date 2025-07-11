package ee.metxdev.tudengibaar.controller;

import ee.metxdev.tudengibaar.DTO.ProductDTO;
import ee.metxdev.tudengibaar.entity.Product;
import ee.metxdev.tudengibaar.service.OrderService;
import ee.metxdev.tudengibaar.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public Product create(@RequestBody ProductDTO body) {
        return productService.save(body);
    }


    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return productService.update(id ,dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }


}
