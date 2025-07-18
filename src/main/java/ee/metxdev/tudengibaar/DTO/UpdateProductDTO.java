package ee.metxdev.tudengibaar.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
}
