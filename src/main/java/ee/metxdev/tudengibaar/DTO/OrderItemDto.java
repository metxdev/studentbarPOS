package ee.metxdev.tudengibaar.DTO;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;
    private int quantity;
}
