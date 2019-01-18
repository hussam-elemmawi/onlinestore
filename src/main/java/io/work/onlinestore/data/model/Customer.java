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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter(onMethod = @__(@JsonIgnore))
    @Getter(onMethod = @__(@JsonProperty))
    private Integer customerId;

    @Setter
    @Getter(onMethod = @__(@JsonProperty))
    private String ssn;

    @Setter
    @Getter(onMethod = @__(@JsonProperty))
    @NotBlank
    private String customerName;

    @Setter(onMethod = @__(@JsonIgnore))
    @Getter(onMethod = @__(@JsonProperty))
    private Timestamp createdAt;

    public Customer(String ssn, @NotBlank String customerName) {
        this.ssn = ssn;
        this.customerName = customerName;
    }

    public Customer(@NotBlank String customerName) {
        this.customerName = customerName;
    }
}
