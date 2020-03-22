package pl.krzysztofurban.springeshopservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.krzysztofurban.springeshopservice.model.Order;
import pl.krzysztofurban.springeshopservice.service.impl.CustomerService;
import pl.krzysztofurban.springeshopservice.service.impl.OrderService;
import pl.krzysztofurban.springeshopservice.service.impl.OrderServiceWithFiegn;
import pl.krzysztofurban.springeshopservice.service.impl.ProductService;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {
    private final OrderServiceWithFiegn orderService;
    private final ProductService productService;
    private final CustomerService customerService;

    @PostMapping(value = "/api/orders", produces = "application/json")
    public ResponseEntity<?> purchaseSampleProduct() throws Exception {
        customerService.registerNewCustomer();
        productService.registerNewProduct();
        Order order = orderService.orderProduct();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(order.getOrderId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
