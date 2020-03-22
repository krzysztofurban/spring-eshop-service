package pl.krzysztofurban.springeshopservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.krzysztofurban.springeshopservice.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
