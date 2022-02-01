package zw.co.nmbz.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import zw.co.nmbz.domain.AccountInformation;
import zw.co.nmbz.domain.CustomerDetails;
import zw.co.nmbz.domain.TransactionDetails;
import zw.co.nmbz.domain.TransferDetails;

@Service
public interface BankingService {

    public List<CustomerDetails> findAll();
    
    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails);
    
    public CustomerDetails findByCustomerNumber(Long customerNumber);
    
    public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber);
    
    public ResponseEntity<Object> deleteCustomer(Long customerNumber) ;
    
    public ResponseEntity<Object> findByAccountNumber(Long accountnumber);
    
    public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber);
    
    public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber);
    
    public List<TransactionDetails> findTransactionsByAccountNumber(Long accountnumber);
    
}
