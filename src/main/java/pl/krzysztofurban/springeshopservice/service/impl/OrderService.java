package pl.krzysztofurban.springeshopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.krzysztofurban.springeshopservice.http.InventoryClient;
import pl.krzysztofurban.springeshopservice.model.Order;
import pl.krzysztofurban.springeshopservice.repository.OrderRepository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "handleInventoryFailure",
            commandProperties = {
                    @HystrixProperty(name =
                            "execution.isolation.thread.timeoutInMilliseconds", value =
                            "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",
                            value ="2")
            })
    public Order orderProduct() {
        Order order = null;

        Map<String, Double> map = null;
        ObjectMapper mapper = new ObjectMapper();

        String resultJson = this.restTemplate.getForObject("http://INVENTORY-SERVICE/inventory/api/inventory" + 1, String.class);
        try {
            map = mapper.readValue(resultJson.getBytes(), HashMap.class);
            log.info("result from inventory service: {}", map);
        } catch (Exception e) {
            log.error("Error while reading back json value: {}", map);
            return null;
        }
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

    private Order handleInventoryFailure() {
        log.error("Cannot connect to inventory service with 20% requests failing in 10seconds interval");
        return null;
    }
}
