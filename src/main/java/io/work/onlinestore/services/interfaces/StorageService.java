package io.work.onlinestore.services.interfaces;

import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    boolean storeProductPhoto(String productId, MultipartFile photo) throws ServiceException;
    String[] getProductPhotosNames(String productId) throws ServiceException;
    Resource loadPhotoFileAsResource(String productId, String photoFileName) throws ServiceException;
    boolean deleteProductPhotos(String productId) throws ServiceException;
}
