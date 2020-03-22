package pl.krzysztofurban.springeshopservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.krzysztofurban.springeshopservice.model.Customer;
import pl.krzysztofurban.springeshopservice.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void registerNewCustomer() {
        Customer customer = new Customer();
        customer.setName("Hillary Clinton");
        customer.setEmail("hilary.clinton@usa.gov.com");
        customer.setPassword("password");
        customerRepository.saveAndFlush(customer);
    }
}
