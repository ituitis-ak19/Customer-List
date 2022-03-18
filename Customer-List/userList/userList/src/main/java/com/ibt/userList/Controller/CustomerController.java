package com.ibt.userList.Controller;

import com.ibt.userList.Dto.CustomerDto;
import com.ibt.userList.Model.ReturnModel;
import com.ibt.userList.Service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
@Api(value = "Customer Api documentation")
public class CustomerController {
    private final CustomerService customerService;
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "List all customers",
            tags = {"customer"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetched customer successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found"
            ),
            @ApiResponse(
                    responseCode = "408",
                    description = "Invalid id supplied"
            ),
    })
    @GetMapping(value = "/customer")
    public ResponseEntity<ReturnModel> getCustomers(){
        logger.debug("Listing all customers");
        List<CustomerDto> customers = customerService.getCustomers();
        logger.debug("All customers listed successfully");
        return new ResponseEntity<>(
                new ReturnModel(HttpStatus.OK.value(), customers, true, "All customers listed successfully"),
                new HttpHeaders(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get customer by id",
            tags = {"customer"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fetched language by id successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid id supplied"
            )
    })
    @GetMapping(value = "/customer/{id}")
    public ResponseEntity<ReturnModel> getCustomerById(@NotNull @PathVariable("id")
                                                       @Parameter(name = "id", example = "1", description = "Customer Id")
                                                       Integer id){
        logger.debug("Getting customer by given id");
        CustomerDto customerDto = customerService.getCustomerById(id);
        logger.debug("Customer found");
        return new ResponseEntity<>(
                new ReturnModel(HttpStatus.OK.value(), customerDto, true, "Customer found"),
                new HttpHeaders(),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Create customer",
            tags = {"customer"}
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created customer"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input was provided"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            ),
    })
    @PostMapping(value = "/customer")
    public  ResponseEntity<ReturnModel> create(@Valid @RequestBody CustomerDto customerDto) {
        logger.debug("Creating customer");
        CustomerDto response = customerService.save(customerDto);
        logger.debug("Customer created successfully");
        return new ResponseEntity<>(
                new ReturnModel(HttpStatus.CREATED.value(), response, true, "Customer created successfully"),
                new HttpHeaders(),
                HttpStatus.CREATED);
    }

    @Operation(
            summary= "Delete Customer by id",
            tags = {"customer"}
    )
    @ApiResponses(
            value={
                    @ApiResponse(responseCode = "204", description = "Delete Customer by id" ),
                    @ApiResponse(responseCode = "404", description = "Customer not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @DeleteMapping(value = "/customer/{id}")
    public ResponseEntity<Void> deleteCustomer(@NotNull @PathVariable("id") @Parameter(name = "id", example = "1", description = "Customer Id")Integer id){
        logger.debug("Deleting customer by given id");
        customerService.deleteCustomerById(id);
        logger.debug("Customer deleted successfully");
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NO_CONTENT);
    }
}
