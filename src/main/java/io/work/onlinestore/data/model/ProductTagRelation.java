package io.work.onlinestore.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
public class ProductTagRelation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    @Getter
    private int id;

    @Getter
    @Setter
    @NotNull
    private Integer productId;

    @Getter
    @Setter
    @NotNull
    private Integer tagId;

    public ProductTagRelation(@NotNull Integer productId, @NotNull Integer tagId) {
        this.productId = productId;
        this.tagId = tagId;
    }
}
