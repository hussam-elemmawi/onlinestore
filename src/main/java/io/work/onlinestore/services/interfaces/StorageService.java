package io.work.onlinestore.services.interfaces;

import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    boolean storeProductPhoto(String productCode, MultipartFile photo) throws ServiceException;
    String[] getProductPhotosNames(String productCode) throws ServiceException;
    Resource loadPhotoFileAsResource(String productCode, String photoFileName) throws ServiceException;
}
