package io.work.onlinestore.data.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customerId", "phone"})
})
@IdClass(Phone.CompositeKey.class)
public class Phone {

    @Id
    @NotNull
    private Integer customerId;

    @Id
    @NotBlank
    private String phone;

    public static class CompositeKey implements Serializable {
        private Integer customerId;
        private Integer phone;
    }
}
