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
public class Promotion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer promotionId;

    @Setter
    @Getter
    private String promotionDescription;

    @Setter
    @Getter
    @NotBlank
    private Float highPrice;

    @Setter
    @Getter
    @NotBlank
    private Float lowPrice;

    @Setter
    @Getter
    @NotBlank
    private Float highPercent;

    @Setter
    @Getter
    @NotBlank
    private Float lowPercent;

    @Setter
    @Getter
    private boolean freeShip;

}
