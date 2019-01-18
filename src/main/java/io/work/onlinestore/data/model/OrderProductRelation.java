package io.work.onlinestore.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"orderId", "productId"})
})
@IdClass(OrderProductRelation.CompositeKey.class)
@NoArgsConstructor
public class OrderProductRelation {

    @Id
    @Getter
    @Setter
    @NotNull
    private Integer orderId;

    @Id
    @Getter
    @Setter
    @NotNull
    private Integer productId;

    @Setter
    @Getter
    @NotNull
    @Min(0)
    private Integer orderQuantity;

    @Setter
    @Getter
    @NotNull
    @Min(0)
    private Float productTotalPrice;


    public static class CompositeKey implements Serializable {
        private Integer orderId;
        private Integer productId;
    }
}
