package com.ibt.userList.Service;

import com.ibt.userList.Controller.CustomerController;
import com.ibt.userList.Dao.CustomerDao;
import com.ibt.userList.Dto.CustomerDto;
import com.ibt.userList.Exception.EntityNotFoundException;
import com.ibt.userList.Model.Customer;
import com.ibt.userList.Util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerDto getCustomerById(Integer userId) {
        Customer customer = customerDao.getCustomerById(userId); //istenilen customer var mı diye kontrol etmek için bir değişkene atıyoruz
        if (customer == null){ //eğer yoksa
            logger.warn("Customer by given id not found");
            throw new EntityNotFoundException(Customer.class, userId.toString());//hata fırlatyıoruz
        }
        return EntityConverter.convertCustomerToCustomerDtoFunction.apply(customer);//var ise customerı dönüyoruz
    }

    public List<CustomerDto> getCustomers(){
        return customerDao.getCustomers().stream() //tüm customerları listeledikten sonra bir stream açıyoruz
                .map(EntityConverter.convertCustomerToCustomerDtoFunction)//hepsini dtoya çeviriyoruz
                .collect(Collectors.toList());//ve listede topluyoruz
    }

    public CustomerDto save(CustomerDto customerDto){
        Customer _customer = customerDao.getCustomerByName(customerDto.getName()); //aynı isimle customer var mı diye kontrol ediyoruz
        if(_customer !=null){ //eğer varsa
            logger.warn("Name has already taken");
            throw new IllegalStateException("Name has already taken"); //hata fırlatıyoruz
        }
        Customer customer = EntityConverter.convertCustomerDtoToCustomerFunction.apply(customerDto);//eğer yoksa dto yu modele dönüştürüyoruz
        customer = customerDao.createCustomer(customer);//customerı yaratıp
        return  EntityConverter.convertCustomerToCustomerDtoFunction.apply(customer);//dto ya çevirip dönüyoruz
    }

    public void deleteCustomerById(Integer userId){
        Customer customer = customerDao.getCustomerById(userId);//verilen id ile customer var mı diye kontrol ediyoruz
        if(customer == null){//eğer yoksa
            logger.warn("Customer by given id not found");
            throw new EntityNotFoundException(Customer.class, userId.toString());//hata fırlatıyoruz
        }
        customerDao.deleteCustomerById(userId);//varsa siliyoruz
    }
}
