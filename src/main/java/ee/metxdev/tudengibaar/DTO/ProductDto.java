package ee.metxdev.tudengibaar.DTO;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String description;
    private double price;
    private Long categoryId;
}
