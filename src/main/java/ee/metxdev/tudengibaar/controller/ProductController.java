package ee.metxdev.tudengibaar.controller;

import ee.metxdev.tudengibaar.DTO.CreateProductDTO;
import ee.metxdev.tudengibaar.DTO.ProductDTO;
import ee.metxdev.tudengibaar.DTO.UpdateProductDTO;
import ee.metxdev.tudengibaar.entity.Product;
import ee.metxdev.tudengibaar.service.OrderService;
import ee.metxdev.tudengibaar.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<ProductDTO> list() {
        return productService.findAll();
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody CreateProductDTO body) {
        ProductDTO savedProduct = productService.save(body);
        return ResponseEntity.ok(savedProduct);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody UpdateProductDTO dto) {
        ProductDTO updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }


}
