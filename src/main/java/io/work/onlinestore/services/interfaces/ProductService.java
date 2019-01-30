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
    Integer create(Product product) throws ServiceException;
    Product getByProductId(Integer productId) throws ServiceException;
    void update(Product product) throws ServiceException;
    void delete(Integer productId) throws ServiceException;
    void addProductTag(Integer productId, Tag tag) throws RecordNotFoundException, ServiceException;
    List<Tag> getProductTags(Integer productId) throws RecordNotFoundException, ServiceException;
    void addPhotoToProduct(Integer productId, MultipartFile photo) throws ServiceException;
    List<String> getAllProductPhotoEndpoints(Integer productId) throws ServiceException;
    Resource getProductPhoto(Integer productId, String photoFileName) throws ServiceException;
    List<Product> filterProductsByTags(List<Tag> tagList) throws ServiceException;
}
