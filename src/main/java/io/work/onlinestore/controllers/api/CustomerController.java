package io.work.onlinestore.controllers.api;

import io.swagger.annotations.ApiOperation;
import io.work.onlinestore.data.model.Customer;
import io.work.onlinestore.data.model.FullCustomer;
import io.work.onlinestore.data.model.Product;
import io.work.onlinestore.services.interfaces.CustomerService;
import io.work.onlinestore.util.exception.ServiceException;
import io.work.onlinestore.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/")
    @ApiOperation(value = "Get all Customers", notes = "Get all Customers information")
    public ResponseEntity<ApiResponse<List<Customer>>> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return new ResponseEntity<>(new ApiResponse<>("Get all customers success", customers), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Get all customers failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{customerId}")
    @ApiOperation(value = "Get a Customer by CustomerId", notes = "Get a Customer information by productId")
    public ResponseEntity<ApiResponse<FullCustomer>> getCustomerByCustomerId(@PathVariable("customerId") Integer customerId) {
        try {
            FullCustomer customer = customerService.getCustomer(customerId);
            return new ResponseEntity<>(new ApiResponse<>("Get product by customerId success", customer), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Get a product failed with customerId " +
                    customerId + " ,error: " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Create customer", notes = "Create a new customer")
    public ResponseEntity<ApiResponse<Integer>> createProduct(@RequestBody @Valid Customer customer) {
        try {
            Integer customerId = customerService.createCustomer(customer);
            return new ResponseEntity<>(new ApiResponse<>("Create customer successfully", customerId), HttpStatus.CREATED);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Create new customer failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{customerId}")
    @ApiOperation(value = "Update customer", notes = "Update a customer")
    public ResponseEntity<ApiResponse<Integer>> updateProduct(@PathVariable("customerId") Integer oldCustomerId, @RequestBody @Valid Customer customer) {
        try {
            customerService.updateCustomer(customer);
            Integer customerId = customer.getCustomerId();
            return new ResponseEntity<>(new ApiResponse<>("Update customer successfully", customerId), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Update customer failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{customerId}")
    @ApiOperation(value = "Delete customer", notes = "Delete all customer information")
    public ResponseEntity<ApiResponse<Integer>> deleteProduct(@PathVariable("customerId") Integer customerId) {
        try {
            customerService.deleteCustomer(customerId);
            return new ResponseEntity<>(new ApiResponse<>("Delete customer successfully", customerId), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Delete customer failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
