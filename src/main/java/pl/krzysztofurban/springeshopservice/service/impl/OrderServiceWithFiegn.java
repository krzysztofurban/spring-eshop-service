package pl.krzysztofurban.springeshopservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.krzysztofurban.springeshopservice.http.InventoryClient;
import pl.krzysztofurban.springeshopservice.model.Order;
import pl.krzysztofurban.springeshopservice.repository.OrderRepository;

import java.util.Map;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class OrderServiceWithFiegn {

    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;

    public Order orderProduct() {
        Order order = null;

        Map<String, Double> map = inventoryClient.getInventory(1L);
        log.info("Result from inventory service throuth feign: {}", map);
        Double qty = map.get("quantity");
        if (qty > 2) {
            order = createOrder(1L, 1L, 2D, 400D);
        }

        log.info("Orders {}", orderRepository.findAll());
        return order;
    }

    public Order createOrder(Long productId, Long customerId, Double quantity, Double price) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setProductId(productId);
        order.setPrice(price);
        order.setQuantity(quantity);
        order = orderRepository.save(order);
        return order;
    }
}
