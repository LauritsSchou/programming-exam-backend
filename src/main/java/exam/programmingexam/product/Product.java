package exam.programmingexam.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
@Id
@GeneratedValue
private int id;
private String name;
private double price;
public Product(String name, double price) {
this.name = name;
this.price = price;
}
}
