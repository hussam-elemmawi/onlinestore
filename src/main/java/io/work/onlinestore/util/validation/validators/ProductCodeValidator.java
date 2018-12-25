package io.work.onlinestore.util.validation.validators;

import io.work.onlinestore.data.model.Product;
import io.work.onlinestore.util.validation.ProductCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class ProductCodeValidator implements ConstraintValidator<ProductCode, String> {

    @Override
    public void initialize(ProductCode productCode) {

    }

    @Override
    public boolean isValid(String productCode, ConstraintValidatorContext constraintValidatorContext) {
        if (productCode == null || productCode.length() != 43) return false;
        try{
            String prefix = productCode.substring(0, Product.PREFIX.length());
            String uuidPart = productCode.substring(Product.PREFIX.length());
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
