package io.work.onlinestore;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.work.onlinestore.controllers.ProductController;
import io.work.onlinestore.data.model.Product;
import io.work.onlinestore.services.interfaces.ProductService;
import io.work.onlinestore.util.response.ApiResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class, secure = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void test_getAllProduct_noProduct() throws Exception {

        List<Product> products = new ArrayList<>();

        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<ApiResponse<List<Product>>> apiResponse =
                new ResponseEntity<>(new ApiResponse<>("Get all products success", products), HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();

        final String driverJson = mapper.writeValueAsString(apiResponse.getBody());

        MvcResult result = mockMvc.perform(get("/api/products/")).andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentType(), contentType.toString());
        assertEquals(result.getResponse().getContentAsString(), driverJson);
    }

    @Test
    public void test_getAllProduct_isFoundOne() throws Exception {

        Product product = Helpers.createMockProduct();

        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<ApiResponse<List<Product>>> apiResponse =
                new ResponseEntity<>(new ApiResponse<>("Get all products success", products), HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(apiResponse.getBody());

        MvcResult result = mockMvc.perform(get("/api/products/")).andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentType(), contentType.toString());
        assertEquals(result.getResponse().getContentAsString(), productJson);
    }

    @Test
    public void test_getAllProduct_isFoundMultiple() throws Exception {
        Product product = Helpers.createMockProduct();
        Product product1 = Helpers.createMockProduct();

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);

        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<ApiResponse<List<Product>>> apiResponse =
                new ResponseEntity<>(new ApiResponse<>("Get all products success", products), HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(apiResponse.getBody());

        MvcResult result = mockMvc.perform(get("/api/products/")).andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentType(), contentType.toString());
        assertEquals(result.getResponse().getContentAsString(), productJson);
    }

    @Test
    public void test_getProductByProductCode_isFound() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();
        when(product.getProductCode()).thenReturn(productCode);

        when(productService.getByProductCode(productCode)).thenReturn(product);

        ResponseEntity<ApiResponse<Product>> apiResponse =
                new ResponseEntity<>(new ApiResponse<>("Get product by productCode success", product), HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(apiResponse.getBody());

        MvcResult result = mockMvc.perform(get("/api/products/{productCode}", productCode)).andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentType(), contentType.toString());
        assertEquals(result.getResponse().getContentAsString(), productJson);
    }

    @Test
    public void test_getProductByProductCode_invalidProductCode_empty() throws Exception {
        String productCode = " ";

        mockMvc.perform(get("/api/products/{productCode}/", productCode)).andExpect(status().isBadRequest());
    }

    @Test
    public void test_getProductByProductCode_invalidProductCode_shortProductCode() throws Exception {
        String productCode = Helpers.generateUniqueProductCode().substring(1); // shorter by 1

        mockMvc.perform(get("/api/products/{productCode}/", productCode)).andExpect(status().isBadRequest());
    }

    @Test
    public void test_getProductByProductCode_invalidProductCode_longProductCode() throws Exception {
        String productCode = Helpers.generateUniqueProductCode() + 'c'; // longer by one

        mockMvc.perform(get("/api/products/{productCode}/", productCode)).andExpect(status().isBadRequest());
    }

    @Test
    public void test_getProductByProductCode_invalidProductCode_notValidPrefix() throws Exception {
        String productCode = Helpers.generateUniqueProductCode().replace('X', 'Z');

        mockMvc.perform(get("/api/products/{productCode}/", productCode)).andExpect(status().isBadRequest());
    }

    @Test
    public void test_getProductByProductCode_invalidProductCode_notValidPostfix() throws Exception {
        String productCode = Helpers.generateUniqueProductCode().replace('-', '^');

        mockMvc.perform(get("/api/products/{productCode}/", productCode)).andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_isCreated() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.data", is(productCode)));
    }

    @Test
    public void test_createProduct_invalidProductName_null() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        // invalid
        when(product.getName()).thenReturn(null);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductName_empty() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        String empty = "";
        // invalid
        when(product.getName()).thenReturn(empty);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductName_blank() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        String blank = "    ";
        // invalid
        when(product.getName()).thenReturn(blank);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductDescription_null() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        // invalid
        when(product.getDescription()).thenReturn(null);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductDescription_empty() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        String empty = "";
        // invalid
        when(product.getDescription()).thenReturn(empty);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductDescription_blank() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        String blank = "    ";
        // invalid
        when(product.getDescription()).thenReturn(blank);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductQuantity_null() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        // invalid
        when(product.getQuantity()).thenReturn(null);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductQuantity_negative() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        int negative = -1;
        // invalid
        when(product.getQuantity()).thenReturn(negative);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductPrice_null() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        // invalid
        when(product.getPrice()).thenReturn(null);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_createProduct_invalidProductPrice_negative() throws Exception {
        String productCode = Helpers.generateUniqueProductCode();

        Product product = Helpers.createMockProduct();

        Float negative = new Float(-13.8);
        // invalid
        when(product.getPrice()).thenReturn(negative);

        when(productService.create(any(Product.class))).thenReturn(productCode);

        ObjectMapper mapper = new ObjectMapper();

        final String productJson = mapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products/").contentType(contentType).content(productJson))
                .andExpect(status().isBadRequest());
    }
}
