package io.work.onlinestore.data.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"orderStatusId", "statusName"})
})
public class OrderStatus {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer orderStatusId;

    @NotBlank
    private String statusName;
}
