package io.work.onlinestore.data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class Tag {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter(onMethod = @__(@JsonIgnore))
    private Integer tagId;

    @Getter(onMethod = @__(@JsonProperty))
    @NotBlank
    private String tagName;

    @Getter(onMethod = @__(@JsonProperty))
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
