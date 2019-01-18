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

@Entity(name = "order_info")
@NoArgsConstructor
@JsonAutoDetect
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer orderId;

    @Setter
    @Getter
    @NotNull
    private Integer customerId;

    @Setter
    @Getter
    @NotNull
    private Integer orderStatusId;

    @Setter
    @Getter
    @NotNull
    @Min(0)
    private Float totalPrice;

    @Setter
    @Getter
    @NotNull
    private Float offerPrice;

    @Setter
    @Getter
    private Integer promotionId;

    @Setter
    @Getter
    @NotNull
    private Timestamp date;

    @Getter
    @Setter
    @NotBlank
    private String shipAddress;

}
