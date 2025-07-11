package ee.metxdev.tudengibaar.service;

import ee.metxdev.tudengibaar.DTO.OrderDTO;
import ee.metxdev.tudengibaar.entity.Order;
import ee.metxdev.tudengibaar.entity.OrderItem;
import ee.metxdev.tudengibaar.entity.Product;
import ee.metxdev.tudengibaar.repository.OrderRepository;
import ee.metxdev.tudengibaar.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Transactional
    public Order save(OrderDTO dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must contain at least one item");
        }

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = dto.getItems().stream().map(itemDto -> {
            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            product.setSalesCount((product.getSalesCount() == null ? 0L : product.getSalesCount()) + itemDto.getQuantity());
            productRepo.save(product);

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setOrder(order);
            return item;
        }).toList();

        order.setItems(items);

        var total = items.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total.doubleValue());

        return orderRepo.save(order);
    }

    @Transactional
    public Order update(Long id, OrderDTO dto) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        order.getItems().clear();

        List<OrderItem> items = dto.getItems().stream().map(itemDto -> {
            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);

        var total = items.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotal(total.doubleValue());

        return orderRepo.save(order);
    }

    public void delete(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        orderRepo.delete(order);
    }
}
