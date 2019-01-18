package io.work.onlinestore.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"productId", "tagId"})
})
@NoArgsConstructor
@IdClass(ProductTagRelation.CompositeKey.class)
public class ProductTagRelation {

    @Id
    @Getter
    @Setter
    @NotNull
    private Integer productId;

    @Id
    @Getter
    @Setter
    @NotNull
    private Integer tagId;

    public ProductTagRelation(@NotNull Integer productId, @NotNull Integer tagId) {
        this.productId = productId;
        this.tagId = tagId;
    }

    public static class CompositeKey implements Serializable {
        private Integer productId;
        private Integer tagId;
    }
}
