package io.work.onlinestore.services.interfaces;

import io.work.onlinestore.data.model.Product;
import io.work.onlinestore.data.model.Tag;
import io.work.onlinestore.util.exception.RecordNotFoundException;
import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts() throws ServiceException;
    String create(Product product) throws ServiceException;
    Product getByProductId(String productId) throws ServiceException;
    void update(Product product) throws ServiceException;
    void addProductTag(String productId, Tag tag) throws RecordNotFoundException, ServiceException;
    List<Tag> getProductTags(String productId) throws RecordNotFoundException, ServiceException;
    void addPhotoToProduct(String productId, MultipartFile photo) throws ServiceException;
    List<String> getAllProductPhotoEndpoints(String productId) throws ServiceException;
    Resource getProductPhoto(String productId, String photoFileName) throws ServiceException;
    List<Product> filterProductsByTags(List<Tag> tagList) throws ServiceException;
}
