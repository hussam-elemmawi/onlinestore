package io.work.onlinestore.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer tagId;

    @Getter
    @Setter
    @NotBlank
    private String tagName;

    @Getter
    @Setter
    @NotBlank
    private String value;

    public Tag(@NotBlank String name, @NotBlank String value) {
        this.tagName = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return tagName + ": " + value;
    }
}
