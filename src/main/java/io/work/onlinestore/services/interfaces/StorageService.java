package io.work.onlinestore.services.interfaces;

import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    boolean storeProductPhoto(Integer productId, MultipartFile photo) throws ServiceException;
    String[] getProductPhotosNames(Integer productId) throws ServiceException;
    Resource loadPhotoFileAsResource(Integer productId, String photoFileName) throws ServiceException;
    boolean deleteProductPhotos(Integer productId) throws ServiceException;
}
