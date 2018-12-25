package io.work.onlinestore.services;

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
    public boolean storeProductPhoto(String productCode, MultipartFile photo) throws ServiceException {
        try {
            String productPhotosDirecotrPath = buildProductPhotoDirectoryPath(productCode);
            File productPhotoDirectory = new File(productPhotosDirecotrPath);
            if (productPhotoDirectory == null || !productPhotoDirectory.exists()) {
                productPhotoDirectory.mkdirs();
            }

            String photoPath = buildPhotoPath(productCode, photo.getOriginalFilename());

            Path newPhotoFilePath = Paths.get(photoPath);

            Files.copy(photo.getInputStream(), newPhotoFilePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (ServiceException | IOException e) {
            logger.info("Can't store product photo, productCode " + productCode + ", photoName " +
                    photo.getOriginalFilename() + " " + e.getMessage() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException(e.getMessage());
        }
        return true;
    }

    @Override
    public String[] getProductPhotosNames(String productCode) throws ServiceException {
        File productPhotosDirectory = new File(buildProductPhotoDirectoryPath(productCode));
        if (productPhotosDirectory != null && productPhotosDirectory.exists()) {
            return productPhotosDirectory.list();
        }
        return new String[0];
    }

    @Override
    public Resource loadPhotoFileAsResource(String productCode, String photoFileName) throws ServiceException  {
        try {
            String photoFilePathString = buildPhotoPath(productCode, photoFileName);
            Path photoFilePath = Paths.get(photoFilePathString);
            Resource resource = new UrlResource(photoFilePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                logger.info("Can't loadPhotoFileAsResource, productCode " + productCode +
                        ", photoName " + photoFileName + " @ " + new Date(System.currentTimeMillis()));
                throw new ServiceException("File not found " + photoFileName);
            }
        } catch (MalformedURLException | ServiceException ex) {
            logger.info("Can't loadPhotoFileAsResource, productCode " + productCode +
                    ", photoName " + photoFileName + " " + ex.getMessage() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("File not found " + photoFileName);
        }
    }

    private String buildProductPhotoDirectoryPath(String productCode) throws ServiceException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(buildBasePhotoDirectoryPath());
        stringBuffer.append(productCode);
        stringBuffer.append('/');
        return stringBuffer.toString();
    }

    private String buildPhotoPath(String productCode, String originalFileName) throws ServiceException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(buildProductPhotoDirectoryPath(productCode));
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
