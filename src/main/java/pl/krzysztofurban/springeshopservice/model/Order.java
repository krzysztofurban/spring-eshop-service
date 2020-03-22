package pl.krzysztofurban.springeshopservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "`Order`")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long orderId;
    private Long productId;
    private Long customerId;
    private Double quantity;
    private Double price;
}
