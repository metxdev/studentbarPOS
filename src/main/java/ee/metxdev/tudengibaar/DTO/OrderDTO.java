package ee.metxdev.tudengibaar.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private List<OrderItemDTO> items;
}
