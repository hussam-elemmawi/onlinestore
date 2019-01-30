package io.work.onlinestore.data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "order_info")
@Data
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter(onMethod = @__(@JsonProperty))
    private Integer orderId;

    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    private Integer customerId;

    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    private Integer orderStatusId;

    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    @Min(0)
    private Float totalPrice;

    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    private Float offerPrice;

    @Getter(onMethod = @__(@JsonProperty))
    private Integer promotionId;

    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    private Timestamp date;

    @Getter(onMethod = @__(@JsonProperty))
    @NotBlank
    private String shipAddress;
}
