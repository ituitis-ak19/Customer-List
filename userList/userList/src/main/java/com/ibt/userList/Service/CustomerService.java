package com.ibt.userList.Service;

import com.ibt.userList.Dao.CustomerDao;
import com.ibt.userList.Dto.CustomerDto;
import com.ibt.userList.Exception.EntityNotFoundException;
import com.ibt.userList.Model.Customer;
import com.ibt.userList.Util.EntityConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerDto getCustomerById(Integer userId) {
        Customer customer = customerDao.getCustomerById(userId);
        if (customer == null){
            throw new EntityNotFoundException(Customer.class, userId.toString());
        }
        return EntityConverter.convertCustomerToCustomerDtoFunction.apply(customer);
    }

    public List<CustomerDto> getCustomers(){
        return customerDao.getCustomers().stream()
                .map(EntityConverter.convertCustomerToCustomerDtoFunction)
                .collect(Collectors.toList());
    }

    public CustomerDto save(CustomerDto customerDto){
        Customer customer = EntityConverter.convertCustomerDtoToCustomerFunction.apply(customerDto);
        customer = customerDao.createCustomer(customer);
        return  EntityConverter.convertCustomerToCustomerDtoFunction.apply(customer);
    }

    public void deleteCustomerById(Integer userId){
        Customer customer = customerDao.getCustomerById(userId);
        if(customer == null){
            throw new EntityNotFoundException(Customer.class, userId.toString());
        }
    }
}
