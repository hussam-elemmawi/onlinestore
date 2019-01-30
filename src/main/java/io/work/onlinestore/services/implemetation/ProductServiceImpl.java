package io.work.onlinestore.services.implemetation;

import io.work.onlinestore.data.model.Product;
import io.work.onlinestore.data.model.ProductTagRelation;
import io.work.onlinestore.data.model.Tag;
import io.work.onlinestore.data.repository.ProductRepository;
import io.work.onlinestore.data.repository.ProductTagRelationRepository;
import io.work.onlinestore.data.repository.TagRepository;
import io.work.onlinestore.services.interfaces.ProductService;
import io.work.onlinestore.services.interfaces.StorageService;
import io.work.onlinestore.util.exception.RecordNotFoundException;
import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProductTagRelationRepository productTagRelationRepository;

    @Autowired
    private StorageService storageService;

    private final static Logger logger = Logger.getLogger(ProductServiceImpl.class.getSimpleName());

    @Override
    public List<Product> getAllProducts() throws ServiceException {
        try {
            List<Product> products = new ArrayList<>();
            productRepository.findAll().forEach(products::add);
            return products;
        } catch (Exception e) {
            logger.info("Can't get all products " + e.getMessage() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't get all products " + e.getMessage());
        }
    }

    @Override
    public Integer create(Product product) throws ServiceException {
        try {
            product.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            product = productRepository.save(product);
            return product.getProductId();
        } catch (Exception e) {
            logger.info("Can't create product " + e.getMessage() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't create product " + e.getMessage());
        }
    }

    @Override
    public Product getByProductId(Integer productId) throws ServiceException {
        try {
            Product product = productRepository.findByProductId(productId);
            if (product == null) {
                throw new RecordNotFoundException("Product not found productId: " + productId);
            }
            return product;
        } catch (Exception e) {
            logger.info("Can't get product by code " + productId + ", " + e.getMessage() +
                    " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void update(Product product) throws ServiceException {
        try {
            Optional<Product> savedProduct = productRepository.findById(product.getProductId());
            if (savedProduct.isPresent()) {
                product.setCreatedAt(savedProduct.get().getCreatedAt());
                product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                productRepository.save(product);
            } else {
                throw new RecordNotFoundException("Product not found.");
            }
        } catch (Exception e) {
            logger.info("Can't update product by code " + product.getProductId() + ", " + e.getMessage() +
                    " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't update product " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer productId) throws ServiceException {
        try {
            productRepository.deleteByProductId(productId);
            storageService.deleteProductPhotos(productId);
        } catch (Exception e) {
            logger.info("Can't delete product by code " + productId + ", " + e.getMessage() +
                    " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't delete product " + e.getMessage());
        }
    }

    @Override
    public void addProductTag(Integer productId, Tag tag) throws RecordNotFoundException, ServiceException {
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new RecordNotFoundException("Product not found: " + productId);
        }
        int productInternalId = product.getProductId();

        Tag savedTag = tagRepository.findByTagNameAndValue(tag.getTagName(), tag.getValue());

        if (savedTag == null) {
            logger.info("Can't add tag for product code " + product.getProductId() + ", Tag not found " +
                    tag.toString() + " @ " + new Date(System.currentTimeMillis()));
            throw new RecordNotFoundException("Tag not found: " + tag.toString());
        }

        try {
            int tagId = savedTag.getTagId();

            addProductTagRelation(new ProductTagRelation(productInternalId, tagId));
        } catch (Exception e) {
            logger.info("Can't add tag for product code " + product.getProductId() +
                    " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't add tag to product " + e.getMessage());
        }
    }

    private void addProductTagRelation(ProductTagRelation productTagRelation) throws ServiceException {
        try {
            productTagRelationRepository.save(productTagRelation);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Tag> getProductTags(Integer productId) throws ServiceException {
        try {
            Product product = productRepository.findByProductId(productId);
            if (product == null) {
                throw new RecordNotFoundException("There is no product with productId " + productId);
            }
            int productInternalId = product.getProductId();

            List<ProductTagRelation> productTagRelationList =
                    productTagRelationRepository.findProductTagRelationByProductId(productInternalId);

            List<Tag> tagList = new ArrayList<>();

            for (ProductTagRelation pr : productTagRelationList) {
                tagList.add(tagRepository.findById(pr.getTagId()).get());
            }
            return tagList;
        } catch (Exception e) {
            logger.info("Can't get tags for product code " + productId +
                    " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't add tag to product " + e.getMessage());
        }
    }

    @Override
    public void addPhotoToProduct(Integer productId, MultipartFile photo) throws ServiceException {
        storageService.storeProductPhoto(productId, photo);
    }

    @Override
    public List<String> getAllProductPhotoEndpoints(Integer productId) throws ServiceException {
        return Arrays.asList(storageService.getProductPhotosNames(productId));
    }

    @Override
    public Resource getProductPhoto(Integer productId, String photoFileName) throws ServiceException {
        return storageService.loadPhotoFileAsResource(productId, photoFileName);
    }

    @Override
    public List<Product> filterProductsByTags(List<Tag> tagList) throws ServiceException {

        Set<Product> productSet = new HashSet<>();
        for (Tag tag : tagList) {
            tag = prepareTag(tag);
            Tag savedTag = tagRepository.findByTagNameAndValue(tag.getTagName(), tag.getValue());
            if (savedTag == null) {
                logger.info("Can't find tag for filtering, " + tag.toString() +
                        " @ " + new Date(System.currentTimeMillis()));
            // throw new RecordNotFoundException("Tag not found: " + tag.toString());
            } else {
                List<ProductTagRelation> productTagRelationList =
                        productTagRelationRepository.findProductTagRelationByTagId(savedTag.getTagId());
                if (productTagRelationList != null && productTagRelationList.size() > 0) {
                    List<Product> products = getAllProductsByProductTagRelations(productTagRelationList);
                    productSet.addAll(products);
                }
            }
        }

        return new ArrayList<>(productSet);
    }

    private Tag prepareTag(Tag tag) {
        if (tag != null) {
            if (tag.getTagName() != null) {
                tag.setTagName(tag.getTagName().trim());
            }

            if (tag.getValue() != null) {
                tag.setValue(tag.getValue().trim());
            }
        }
        return tag;
    }

    private List<Product> getAllProductsByProductTagRelations(List<ProductTagRelation> productTagRelationList) {
        List<Product> productList = new ArrayList<>();

        for (ProductTagRelation ptr: productTagRelationList) {
            Optional<Product> optionalProduct = productRepository.findById(ptr.getProductId());

            if (optionalProduct != null && optionalProduct.isPresent()) {
                productList.add(optionalProduct.get());
            }
        }

        return productList;
    }
}
