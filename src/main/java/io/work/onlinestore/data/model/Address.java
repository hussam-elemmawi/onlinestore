package io.work.onlinestore.data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customerId", "address"})
})
@IdClass(Address.CompositeKey.class)
public class Address {

    @Id
    @NotNull
    private Integer customerId;

    @Id
    @NotBlank
    private String address;

    public static class CompositeKey implements Serializable {
        private Integer customerId;
        private Integer address;
    }
}
