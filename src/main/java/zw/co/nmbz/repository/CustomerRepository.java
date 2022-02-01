package zw.co.nmbz.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import zw.co.nmbz.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

    public Optional<Customer> findByCustomerNumber(Long customerNumber);
    
}
