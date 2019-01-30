package io.work.onlinestore;

import io.work.onlinestore.data.model.Product;

import java.sql.Timestamp;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class Helpers {

    public static Product createMockProduct() {
        Product product = mock(Product.class);
        when(product.getProductId()).thenReturn(1);
        when(product.getProductName()).thenReturn("product name");
        when(product.getProductDescription()).thenReturn("product description");
        when(product.getQuantity()).thenReturn(100);
        when(product.getPrice()).thenReturn(20.0f);
        when(product.getOfferPrice()).thenReturn(15.0f);
        when(product.getCreatedAt()).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(product.getUpdatedAt()).thenReturn(new Timestamp(System.currentTimeMillis()));
        when(product.getCode()).thenReturn("prod_code");
        return product;
    }
}
