package io.work.onlinestore.data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"orderId", "productId"})
})
@IdClass(OrderProductRelation.CompositeKey.class)
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)

public class OrderProductRelation {

    @Id
    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    private Integer orderId;

    @Id
    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    private Integer productId;

    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    @Min(0)
    private Integer orderQuantity;

    @Getter(onMethod = @__(@JsonProperty))
    @NotNull
    @Min(0)
    private Float productTotalPrice;


    public static class CompositeKey implements Serializable {
        private Integer orderId;
        private Integer productId;
    }
}
