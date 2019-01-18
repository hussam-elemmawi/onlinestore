package io.work.onlinestore.data.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customerId", "phone"})
})
@IdClass(Phone.CompositeKey.class)
public class Phone {

    @Id
    @Getter
    @Setter
    @NotNull
    private Integer customerId;

    @Id
    @Getter
    @Setter
    @NotBlank
    private String phone;

    public static class CompositeKey implements Serializable {
        private Integer customerId;
        private Integer phone;
    }

}
