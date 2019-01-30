package io.work.onlinestore.util.validation.validators;

import io.work.onlinestore.util.validation.ProductId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProductIdValidator implements ConstraintValidator<ProductId, Integer> {

    @Override
    public void initialize(ProductId productId) {

    }

    @Override
    public boolean isValid(Integer productId, ConstraintValidatorContext constraintValidatorContext) {
        return productId != null && productId > 0;
    }
}
