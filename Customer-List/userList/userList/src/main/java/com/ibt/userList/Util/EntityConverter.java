package com.ibt.userList.Util;

import com.ibt.userList.Dto.CustomerDto;
import com.ibt.userList.Model.Customer;

import java.util.function.Function;

public interface EntityConverter {
    Function<Customer, CustomerDto> convertCustomerToCustomerDtoFunction =
            (customer -> new CustomerDto(
                    customer.getId(),
                    customer.getName(),
                    customer.getSurname()
            ));

    Function<CustomerDto, Customer> convertCustomerDtoToCustomerFunction =
            (customerDto -> new Customer(
                    customerDto.getId(),
                    customerDto.getName(),
                    customerDto.getSurname()
            ));
}
