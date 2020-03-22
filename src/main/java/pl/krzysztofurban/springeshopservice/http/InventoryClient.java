package pl.krzysztofurban.springeshopservice.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

@FeignClient("INVENTORY-SERVICE")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.GET, value = "/inventory/api/inventory/{productId}")
    HashMap<String, Double> getInventory(@PathVariable("productId") Long productId);
}
