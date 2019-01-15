package io.work.onlinestore.data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    @Getter
    private int id;

    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    private String productId;

    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    @NotBlank
    private String name;

    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    @NotBlank
    private String description;

    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    @NotNull
    @Min(0)
    private Integer quantity;

    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    @NotNull
    @Min(0)
    private Float price;

    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    private Timestamp createdAt;

    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    private Timestamp updatedAt;

    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    @NotBlank
    private String code;

    public static final String PREFIX = "PRODXX_";

    public Product(String name, String description, Integer quantity, Float price, String code) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        this.code = code;
    }

    public Product(String name, String description, Integer quantity, Float price, Timestamp createdAt, Timestamp updatedAt, String code) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.code = code;
    }
}
