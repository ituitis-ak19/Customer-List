package com.ibt.userList.Controller;

import com.ibt.userList.Dto.CustomerDto;
import com.ibt.userList.Model.ReturnModel;
import com.ibt.userList.Service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
public class CustomerController {
    private final CustomerService customerService;

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
        List<CustomerDto> customers = customerService.getCustomers();
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
        CustomerDto customerDto = customerService.getCustomerById(id);
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
        CustomerDto response = customerService.save(customerDto);
        return new ResponseEntity<>(
                new ReturnModel(HttpStatus.CREATED.value(), response, true, "Customer created successfully"),
                new HttpHeaders(),
                HttpStatus.CREATED);
    }
}
