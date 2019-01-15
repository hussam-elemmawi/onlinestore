package io.work.onlinestore.util.validation.validators;

import io.work.onlinestore.data.model.Product;
import io.work.onlinestore.util.validation.ProductId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class ProductIdValidator implements ConstraintValidator<ProductId, String> {

    @Override
    public void initialize(ProductId productId) {

    }

    @Override
    public boolean isValid(String productId, ConstraintValidatorContext constraintValidatorContext) {
        if (productId == null || productId.length() != 43) return false;
        try{
            String prefix = productId.substring(0, Product.PREFIX.length());
            String uuidPart = productId.substring(Product.PREFIX.length());
            if (!prefix.equals(Product.PREFIX) || uuidPart.length() != 36) {
                return false;
            }
            UUID.fromString(uuidPart);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
