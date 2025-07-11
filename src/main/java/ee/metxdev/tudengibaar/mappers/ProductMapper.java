package ee.metxdev.tudengibaar.mappers;

import ee.metxdev.tudengibaar.DTO.CreateProductDTO;
import ee.metxdev.tudengibaar.DTO.ProductDTO;
import ee.metxdev.tudengibaar.entity.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getName() != null ? product.getCategory().getName() : null
        );
    }

    public static Product toEntity(CreateProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        return product;
    }



}
