package io.work.onlinestore.data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(onMethod = @__(@JsonProperty))
    @Getter(onMethod = @__(@JsonProperty))
    private Integer productId;

    @Getter(onMethod = @__(@JsonProperty))
    @NotBlank
    private String productName;

    @Getter(onMethod = @__(@JsonProperty))
    @NotBlank
    private String productDescription;


    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    @Min(0)
    private Integer quantity;


    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    @Min(0)
    private Float price;


    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    @Min(0)
    private Float offerPrice;


    @Setter(onMethod = @__(@JsonIgnore))
    @Getter(onMethod = @__(@JsonProperty))
    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    private Timestamp createdAt;


    @Setter(onMethod = @__(@JsonIgnore))
    @Getter(onMethod = @__(@JsonProperty))
    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    private Timestamp updatedAt;


    @Getter(onMethod = @__(@JsonProperty))
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
