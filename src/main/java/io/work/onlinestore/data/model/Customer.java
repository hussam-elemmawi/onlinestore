package io.work.onlinestore.data.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
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
@Data
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter(onMethod = @__(@JsonProperty))
    @Getter(onMethod = @__(@JsonProperty))
    private Integer customerId;

    @Getter(onMethod = @__(@JsonProperty))
    private String ssn;

    @Getter(onMethod = @__(@JsonProperty))
    @NotBlank
    private String customerName;

    @Setter(onMethod = @__(@JsonIgnore))
    @Getter(onMethod = @__(@JsonProperty))
    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    private Timestamp createdAt;

    public Customer(String ssn, @NotBlank String customerName) {
        this.ssn = ssn;
        this.customerName = customerName;
    }

    public Customer(@NotBlank String customerName) {
        this.customerName = customerName;
    }
}
