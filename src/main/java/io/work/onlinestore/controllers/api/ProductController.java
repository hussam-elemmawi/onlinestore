package io.work.onlinestore.controllers.api;

import io.swagger.annotations.ApiOperation;
import io.work.onlinestore.data.model.Product;
import io.work.onlinestore.data.model.Tag;
import io.work.onlinestore.services.interfaces.ProductService;
import io.work.onlinestore.util.exception.RecordNotFoundException;
import io.work.onlinestore.util.exception.ServiceException;
import io.work.onlinestore.util.response.ApiResponse;
import io.work.onlinestore.util.validation.ProductId;
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
import javax.validation.constraints.Min;
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

    @GetMapping(path = "/{productId}")
    @ApiOperation(value = "Get a product by productId", notes = "Get a product information by productId")
    public ResponseEntity<ApiResponse<Product>> getProductByProductId(@PathVariable("productId") @ProductId Integer productId) {
        try {
            Product product = productService.getByProductId(productId);
            return new ResponseEntity<>(new ApiResponse<>("Get product by productId success", product), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Get a product failed with productId " +
                    productId + " ,error: " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Create product", notes = "Create a new product")
    public ResponseEntity<ApiResponse<Integer>> createProduct(@RequestBody @Valid Product product) {
        try {
            Integer productId = productService.create(product);
            return new ResponseEntity<>(new ApiResponse<>("Create product successfully", productId), HttpStatus.CREATED);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Create new product failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/")
    @ApiOperation(value = "Update product", notes = "Update a product")
    public ResponseEntity<ApiResponse<Integer>> updateProduct(@RequestBody @Valid Product product) {
        try {
            productService.update(product);
            Integer productId = product.getProductId();
            return new ResponseEntity<>(new ApiResponse<>("Update product successfully", productId), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Update product failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{productId}")
    @ApiOperation(value = "Delete product", notes = "Delete all product information")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable("productId") @ProductId Integer productId) {
        try {
            productService.delete(productId);
            return new ResponseEntity<>(new ApiResponse<>("Delete product successfully", productId.toString()), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Delete product failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "{productId}/tags")
    @ApiOperation(value = "Add product tags", notes = "Add new tag(s) to a product")
    public ResponseEntity<List<ApiResponse<String>>> addProductTags(@PathVariable("productId") @ProductId Integer productId,
                                                                   @RequestBody @Valid List<Tag> tags) {

        List<ApiResponse<String>> responses = new ArrayList<>();
        for (Tag tag : tags) {
            try {
                productService.addProductTag(productId, tag);
                responses.add(new ApiResponse<>("Product tag added. ", productId.toString()));
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

    @GetMapping(path = "{productId}/tags")
    @ApiOperation(value = "Get product tags", notes = "Get new tag(s) to a product")
    public ResponseEntity<ApiResponse<List<Tag>>> getProductTags(@PathVariable("productId") @ProductId Integer productId) {
        try {
            List<Tag> productTagList = productService.getProductTags(productId);
            return new ResponseEntity<>(new ApiResponse<>("Product tags with productId " + productId, productTagList), HttpStatus.OK);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>( "Product doesn't exist: " + productId, null), HttpStatus.NOT_FOUND);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new ApiResponse<>("Get product tags failed " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/{productId}/photo")
    @ApiOperation(value = "Add a photo to product", notes = "Add a new photo product")
    public ResponseEntity<ApiResponse<String>> createProduct(@PathVariable("productId") @ProductId Integer productId, @RequestParam("photo") @NotNull MultipartFile photo) {
        try {
            productService.addPhotoToProduct(productId, photo);
            return new ResponseEntity<>(new ApiResponse<>("Product photo uploading success ", productId.toString()), HttpStatus.CREATED);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Product photo uploading failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}/photo")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<String>>> serveFile(@PathVariable("productId") @ProductId Integer productId) {
        try {
            List<String> photosFileName = productService.getAllProductPhotoEndpoints(productId);
            return new ResponseEntity<>(new ApiResponse<>("Get all product photos file names success", photosFileName), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(new ApiResponse<>("Get all product photos file names failed " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}/photo/{photoFileName}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable("productId") @ProductId Integer productId,
                                                 @PathVariable("photoFileName") @NotBlank String photoFileName, HttpServletRequest request) {

        try {
            Resource resource = productService.getProductPhoto(productId, photoFileName);

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
