package io.work.onlinestore;

import io.work.onlinestore.data.model.Product;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class Helpers {

    public static Product createMockProduct() {
        Product product = mock(Product.class);
        when(product.getProductCode()).thenReturn(generateUniqueProductCode());
        when(product.getName()).thenReturn("product name");
        when(product.getDescription()).thenReturn("product description");
        when(product.getQuantity()).thenReturn(100);
        when(product.getPrice()).thenReturn(20.0f);
        return product;
    }

    public static String generateUniqueProductCode() {
        return Product.PREFIX + UUID.randomUUID();
    }
}
