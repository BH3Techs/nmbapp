package zw.co.nmbz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import zw.co.nmbz.model.CustomerAccountXRef;

@Repository
public interface CustomerAccountXRefRepository extends CrudRepository<CustomerAccountXRef, String> {

}
