package io.work.onlinestore.data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@JsonAutoDetect
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer productId;

    @Setter
    @Getter
    @NotBlank
    private String productName;

    @Setter
    @Getter
    @NotBlank
    private String productDescription;


    @Setter
    @Getter
    @NotNull
    @Min(0)
    private Integer quantity;


    @Setter
    @Getter
    @NotNull
    @Min(0)
    private Float price;


    @Setter
    @Getter
    @NotNull
    @Min(0)
    private Float offerPrice;


    @Setter
    @Getter
    private Timestamp createdAt;


    @Setter
    @Getter
    private Timestamp updatedAt;


    @Setter
    @Getter
    @NotBlank
    private String code;

    public Product(String productName, String productDescription, Integer quantity, Float price, Float offerPrice, String code) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.price = price;
        this.offerPrice = offerPrice;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        this.code = code;
    }

    public Product(String productName, String productDescription, Integer quantity, Float price, Float offerPrice, Timestamp createdAt, Timestamp updatedAt, String code) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.price = price;
        this.offerPrice = offerPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.code = code;
    }
}
