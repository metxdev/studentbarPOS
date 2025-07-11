package ee.metxdev.tudengibaar.controller;

import ee.metxdev.tudengibaar.DTO.OrderDTO;
import ee.metxdev.tudengibaar.entity.Order;
import ee.metxdev.tudengibaar.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> list() {
        return orderService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody OrderDTO orderDto) {
        return orderService.save(orderDto);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody OrderDTO dto) {
        return orderService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}