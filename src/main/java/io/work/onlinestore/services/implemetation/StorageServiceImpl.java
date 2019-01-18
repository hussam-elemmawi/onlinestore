package io.work.onlinestore.services.implemetation;

import io.work.onlinestore.services.interfaces.StorageService;
import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private Environment environment;

    private final static Logger logger = Logger.getLogger(StorageServiceImpl.class.getSimpleName());

    @Override
    public boolean storeProductPhoto(String productId, MultipartFile photo) throws ServiceException {
        try {
            String productPhotosDirecotrPath = buildProductPhotoDirectoryPath(productId);
            File productPhotoDirectory = new File(productPhotosDirecotrPath);
            if (productPhotoDirectory == null || !productPhotoDirectory.exists()) {
                productPhotoDirectory.mkdirs();
            }

            String photoPath = buildPhotoPath(productId, photo.getOriginalFilename());

            Path newPhotoFilePath = Paths.get(photoPath);

            Files.copy(photo.getInputStream(), newPhotoFilePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (ServiceException | IOException e) {
            logger.info("Can't store product photo, productId " + productId + ", photoName " +
                    photo.getOriginalFilename() + " " + e.getMessage() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    @Override
    public String[] getProductPhotosNames(String productId) throws ServiceException {
        File productPhotosDirectory = new File(buildProductPhotoDirectoryPath(productId));
        if (productPhotosDirectory != null && productPhotosDirectory.exists()) {
            return productPhotosDirectory.list();
        }
        return new String[0];
    }

    @Override
    public Resource loadPhotoFileAsResource(String productId, String photoFileName) throws ServiceException  {
        try {
            String photoFilePathString = buildPhotoPath(productId, photoFileName);
            Path photoFilePath = Paths.get(photoFilePathString);
            Resource resource = new UrlResource(photoFilePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                logger.info("Can't loadPhotoFileAsResource, productId " + productId +
                        ", photoName " + photoFileName + " @ " + new Date(System.currentTimeMillis()));
                throw new ServiceException("File not found " + photoFileName);
            }
        } catch (MalformedURLException | ServiceException ex) {
            logger.info("Can't loadPhotoFileAsResource, productId " + productId +
                    ", photoName " + photoFileName + " " + ex.getMessage() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("File not found " + photoFileName);
        }
    }

    @Override
    public boolean deleteProductPhotos(String productId) throws ServiceException {
        File productPhotosDirectory = new File(buildProductPhotoDirectoryPath(productId));
        boolean success = true;
        if (productPhotosDirectory.exists()) {
            String[] productPhotos = productPhotosDirectory.list();
            if (productPhotos != null && productPhotos.length > 0) {
                for (String photoName: productPhotos) {
                    try {
                        String photoPathString = buildPhotoPath(productId, photoName);

                        Path photoPath = Paths.get(photoPathString);

                        Files.delete(photoPath);
                    } catch (ServiceException | IOException ex) {
                        success = false;
                    }
                }
            }
        }

        return success;
    }

    private String buildProductPhotoDirectoryPath(String productId) throws ServiceException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(buildBasePhotoDirectoryPath());
        stringBuffer.append(productId);
        stringBuffer.append('/');
        return stringBuffer.toString();
    }

    private String buildPhotoPath(String productId, String originalFileName) throws ServiceException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(buildProductPhotoDirectoryPath(productId));
        stringBuffer.append(originalFileName);
        return stringBuffer.toString();
    }

    private String buildBasePhotoDirectoryPath() throws ServiceException {
        String basePhotoDirectoryPath = environment.getProperty("base.photos.directory.path");
        if (basePhotoDirectoryPath == null || basePhotoDirectoryPath.trim().isEmpty()) {
            throw new ServiceException("Photos directory path can't be null or empty");
        }
        File photosDirectory = new File(basePhotoDirectoryPath);
        if (!photosDirectory.exists()) photosDirectory.mkdirs();
        return basePhotoDirectoryPath;
    }
}
