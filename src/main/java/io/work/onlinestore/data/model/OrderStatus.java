package io.work.onlinestore.data.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"orderStatusId", "statusName"})
})
public class OrderStatus {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer orderStatusId;

    @Getter
    @Setter
    @NotBlank
    private String statusName;

}
