package pl.krzysztofurban.springeshopservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzysztofurban.springeshopservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
