package com.ibt.userList.Dao;

import com.ibt.userList.Model.Customer;
import com.ibt.userList.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDao {
    private final CustomerRepository customerRepository;

    public CustomerDao(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Integer userId ){
        return customerRepository.findById(userId).isPresent() ?
                customerRepository.findById(userId).get() :
                null;
    }

    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public void deleteCustomerById(Integer userId){
        customerRepository.deleteById(userId);
    }

    public Customer getCustomerByName(String name){return customerRepository.findByName(name);}
}
