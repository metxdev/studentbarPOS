package ee.metxdev.tudengibaar.DTO;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private int quantity;
}
