package pl.krzysztofurban.springeshopservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.krzysztofurban.springeshopservice.model.Product;
import pl.krzysztofurban.springeshopservice.repository.ProductRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void registerNewProduct() {
        Product product = new Product();
        product.setName("Superb Java");
        product.setPrice(200D);
        product.setQuantity(3D);
        productRepository.save(product);
    }
}
