package io.work.onlinestore.controllers;

import io.swagger.annotations.ApiOperation;
import io.work.onlinestore.data.model.Product;
import io.work.onlinestore.data.model.Tag;
import io.work.onlinestore.services.interfaces.ProductService;
import io.work.onlinestore.util.exception.RecordNotFoundException;
import io.work.onlinestore.util.exception.ServiceException;
import io.work.onlinestore.util.response.ApiResponse;
import io.work.onlinestore.util.validation.ProductCode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/")
    @ApiOperation(value = "Get all products", notes = "Get all products information")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return new ResponseEntity<>(new ApiResponse<>("Get all products success", products), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Get all products failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{productCode}")
    @ApiOperation(value = "Get a product by productCode", notes = "Get a product information by productCode")
    public ResponseEntity<ApiResponse<Product>> getProductByProductCode(@PathVariable("productCode") @ProductCode String productCode) {
        try {
            Product product = productService.getByProductCode(productCode);
            return new ResponseEntity<>(new ApiResponse<>("Get product by productCode success", product), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Get a product failed with productCode " +
                    productCode + " ,error: " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Create product", notes = "Create a new product")
    public ResponseEntity<ApiResponse<String>> createProduct(@RequestBody @Valid Product product) {
        try {
            String productCode = productService.create(product);
            return new ResponseEntity<>(new ApiResponse<>("Create product successfully", productCode), HttpStatus.CREATED);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Create new product failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/")
    @ApiOperation(value = "Update product", notes = "Update a new product")
    public ResponseEntity<ApiResponse<String>> updateProduct(@RequestBody @Valid Product product) {
        try {
            productService.update(product);
            String productCode = product.getProductCode();
            return new ResponseEntity<>(new ApiResponse<>("Update product successfully", productCode), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Update product failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "{productCode}/tags")
    @ApiOperation(value = "Add product tags", notes = "Add new tag(s) to a product")
    public ResponseEntity<List<ApiResponse<String>>> addProductTags(@PathVariable("productCode") @NotBlank String productCode,
                                                                   @RequestBody @Valid List<Tag> tags) {

        List<ApiResponse<String>> responses = new ArrayList<>();
        for (Tag tag : tags) {
            try {
                productService.addProductTag(productCode, tag);
                responses.add(new ApiResponse<>("Product tag added. ", productCode));
            } catch (RecordNotFoundException e) {
                // return immediately no need to continue the loop
                responses.add(
                        new ApiResponse<>( "Data doesn't exist. " + e.getMessage(), null));
            } catch (ServiceException e) {
                responses.add(new ApiResponse<>("Product tag add failed. ", e.getMessage()));
            }
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(path = "{productCode}/tags")
    @ApiOperation(value = "Get product tags", notes = "Get new tag(s) to a product")
    public ResponseEntity<ApiResponse<List<Tag>>> getProductTags(@PathVariable("productCode") @NotBlank String productCode) {
        try {
            List<Tag> productTagList = productService.getProductTags(productCode);
            return new ResponseEntity<>(new ApiResponse<>("Product tags with productCode " + productCode, productTagList), HttpStatus.OK);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>( "Product doesn't exist: " + productCode, null), HttpStatus.NOT_FOUND);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new ApiResponse<>("Get product tags failed " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/{productCode}/photo")
    @ApiOperation(value = "Add a photo to product", notes = "Add a new photo product")
    public ResponseEntity<ApiResponse<String>> createProduct(@PathVariable("productCode") @NotBlank String productCode, @RequestParam("photo") @NotNull MultipartFile photo) {
        try {
            productService.addPhotoToProduct(productCode, photo);
            return new ResponseEntity<>(new ApiResponse<>("Product photo uploading success ", productCode), HttpStatus.CREATED);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Product photo uploading failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productCode}/photo")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<String>>> serveFile(@PathVariable("productCode") @NotBlank String productCode) {
        try {
            List<String> photosFileName = productService.getAllProductPhotoEndpoints(productCode);
            return new ResponseEntity<>(new ApiResponse<>("Get all product photos file names success", photosFileName), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new ApiResponse<>("Get all product photos file names failed " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productCode}/photo/{photoFileName}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable("productCode") @NotBlank String productCode,
                                                 @PathVariable("photoFileName") @NotBlank String photoFileName, HttpServletRequest request) {

        try {
            Resource resource = productService.getProductPhoto(productCode, photoFileName);

            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                // logger.info("Could not determine file type.");
            }

            // Fallback to the default content type if type could not be determined
            if(contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (ServiceException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(path = "/filters")
    @ApiOperation(value = "Filter products by tags", notes = "Filter drivers information by tags")
    public ResponseEntity<ApiResponse<List<Product>>> filterProductByTags(@RequestBody @Valid List<Tag> tagList) {
        try {
            List<Product> productList = productService.filterProductsByTags(tagList);
            return new ResponseEntity<>(new ApiResponse<>("Filter products by tags success", productList), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new ApiResponse<>("Filter products by tags failed " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
