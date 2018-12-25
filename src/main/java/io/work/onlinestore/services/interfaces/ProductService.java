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
    Product getByProductCode(String productCode) throws ServiceException;
    void update(Product product) throws ServiceException;
    void addProductTag(String productCode, Tag tag) throws RecordNotFoundException, ServiceException;
    List<Tag> getProductTags(String productCode) throws RecordNotFoundException, ServiceException;
    void addPhotoToProduct(String productCode, MultipartFile photo) throws ServiceException;
    List<String> getAllProductPhotoEndpoints(String productCode) throws ServiceException;
    Resource getProductPhoto(String productCode, String photoFileName) throws ServiceException;
    List<Product> filterProductsByTags(List<Tag> tagList) throws ServiceException;
}
