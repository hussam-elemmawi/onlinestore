package io.work.onlinestore.data.model;

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
public class Promotion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer promotionId;

    private String promotionDescription;

    @NotBlank
    private Float highPrice;

    @NotBlank
    private Float lowPrice;

    @NotBlank
    private Float highPercent;

    @NotBlank
    private Float lowPercent;

    private boolean freeShip;
}
