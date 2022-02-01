package zw.co.nmbz.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zw.co.nmbz.domain.AccountInformation;
import zw.co.nmbz.domain.CustomerDetails;
import zw.co.nmbz.domain.TransactionDetails;
import zw.co.nmbz.domain.TransferDetails;
import zw.co.nmbz.model.Account;
import zw.co.nmbz.model.Customer;
import zw.co.nmbz.model.CustomerAccountXRef;
import zw.co.nmbz.model.Transaction;
import zw.co.nmbz.repository.AccountRepository;
import zw.co.nmbz.repository.CustomerAccountXRefRepository;
import zw.co.nmbz.repository.CustomerRepository;
import zw.co.nmbz.repository.TransactionRepository;
import zw.co.nmbz.service.helper.BankingServiceHelper;

@Service
@Transactional
public class BankingServiceImpl implements BankingService {

	@Autowired
    private CustomerRepository customerRepository;
	@Autowired
    private AccountRepository accountRepository;
	@Autowired
    private TransactionRepository transactionRepository;
	@Autowired
    private CustomerAccountXRefRepository custAccXRefRepository;
    @Autowired
    private BankingServiceHelper bankingServiceHelper;

    public BankingServiceImpl(CustomerRepository repository) {
        this.customerRepository=repository;
    }
    
   
    public List<CustomerDetails> findAll() {
    	
    	List<CustomerDetails> allCustomerDetails = new ArrayList<>();

        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer -> {
        	allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer));
        });
        
        return allCustomerDetails;
    }

    /**
     * CREATE Customer
     * 
     * @param customerDetails
     * @return
     */
	public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails) {
		
		Customer customer = bankingServiceHelper.convertToCustomerEntity(customerDetails);
		customer.setCreationdate(LocalDate.now());
		customerRepository.save(customer);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("New Customer created successfully.");
	}

	/**
	 * READ Customer
	 * 
	 * @param customerNumber
	 * @return
	 */
    
	public CustomerDetails findByCustomerNumber(Long customerNumber) {
		
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		if(customerEntityOpt.isPresent())
			return bankingServiceHelper.convertToCustomerDomain(customerEntityOpt.get());
		
		return null;
	}

	/**
	 * UPDATE Customer
	 * 
	 * @param customerDetails
	 * @param customerNumber
	 * @return
	 */
	public ResponseEntity<Object> updateCustomer(CustomerDetails customerDetails, Long customerNumber) {
		Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
		Customer unmanagedCustomerEntity = bankingServiceHelper.convertToCustomerEntity(customerDetails);
		if(managedCustomerEntityOpt.isPresent()) {
			Customer managedCustomerEntity = managedCustomerEntityOpt.get();
			
			managedCustomerEntity.setFirstName(unmanagedCustomerEntity.getFirstName());
			managedCustomerEntity.setSurname(unmanagedCustomerEntity.getSurname());
			managedCustomerEntity.setNationalid(unmanagedCustomerEntity.getNationalid());
			managedCustomerEntity.setTitle(unmanagedCustomerEntity.getTitle());
			managedCustomerEntity.setHomeowner(unmanagedCustomerEntity.getHomeowner());
			managedCustomerEntity.setEmail(unmanagedCustomerEntity.getEmail());
			managedCustomerEntity.setCreationdate(LocalDate.now());
			managedCustomerEntity.setGender(unmanagedCustomerEntity.getGender());
			managedCustomerEntity.setUsername(unmanagedCustomerEntity.getUsername());
			
			
			customerRepository.save(managedCustomerEntity);
			
			return ResponseEntity.status(HttpStatus.OK).body("Success: Customer updated.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
		}
	}

	/**
	 * DELETE Customer
	 * 
	 * @param customerNumber
	 * @return
	 */
	public ResponseEntity<Object> deleteCustomer(Long customerNumber) {
		
		Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		if(managedCustomerEntityOpt.isPresent()) {
			Customer managedCustomerEntity = managedCustomerEntityOpt.get();
			customerRepository.delete(managedCustomerEntity);
			return ResponseEntity.status(HttpStatus.OK).body("Success: Customer deleted.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer does not exist.");
		}
		
		//TODO: Delete all customer entries from CustomerAccountXRef
	}

	/**
	 * Find Account
	 * 
	 * @param accountnumber
	 * @return
	 */
	public ResponseEntity<Object> findByAccountNumber(Long accountnumber) {
		
		Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountnumber);

		if(accountEntityOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(bankingServiceHelper.convertToAccountDomain(accountEntityOpt.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Number " + accountnumber + " not found.");
		}
		
	}

	/**
	 * Create new account
	 * 
	 * @param accountInformation
	 * @param customerNumber 
	 * 
	 * @return
	 */
	public ResponseEntity<Object> addNewAccount(AccountInformation accountInformation, Long customerNumber) {
		
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		if(customerEntityOpt.isPresent()) {
			accountRepository.save(bankingServiceHelper.convertToAccountEntity(accountInformation));
			
			// Add an entry to the CustomerAccountXRef
			custAccXRefRepository.save(CustomerAccountXRef.builder()
					.accountNumber(accountInformation.getAccountnumber())
					.customerNumber(customerNumber)
					.build());
			
		}

		return ResponseEntity.status(HttpStatus.CREATED).body("New Account created successfully.");
	}

	/**
	 * Transfer funds from one account to another for a specific customer
	 * 
	 * @param transferDetails
	 * @param customerNumber
	 * @return
	 */
	public ResponseEntity<Object> transferDetails(TransferDetails transferDetails, Long customerNumber) {
		
		List<Account> accountEntities = new ArrayList<>();
		Account fromAccountEntity = null;
		Account toAccountEntity = null;
		
		Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

		// If customer is present
		if(customerEntityOpt.isPresent()) {
			
			// get FROM ACCOUNT info
			Optional<Account> fromAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getFromAccountNumber());
			if(fromAccountEntityOpt.isPresent()) {
				fromAccountEntity = fromAccountEntityOpt.get();
			}
			else {
			// if from request does not exist, 404 Bad Request
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + transferDetails.getFromAccountNumber() + " not found.");
			}
			
			
			// get TO ACCOUNT info
			Optional<Account> toAccountEntityOpt = accountRepository.findByAccountNumber(transferDetails.getToAccountNumber());
			if(toAccountEntityOpt.isPresent()) {
				toAccountEntity = toAccountEntityOpt.get();
			}
			else {
			// if from request does not exist, 404 Bad Request
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("To Account Number " + transferDetails.getToAccountNumber() + " not found.");
			}

			
			// if not sufficient funds, return 400 Bad Request
			if(fromAccountEntity.getActivebalance() < transferDetails.getTransferAmount()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds.");
			}
			else {
				synchronized (this) {
					// update FROM ACCOUNT 
					fromAccountEntity.setActivebalance(fromAccountEntity.getActivebalance() - transferDetails.getTransferAmount()-(0.02*(transferDetails.getTransferAmount())));
					fromAccountEntity.setUpdateDatetime(LocalDate.now());
					accountEntities.add(fromAccountEntity);
					
					// update TO ACCOUNT
					toAccountEntity.setActivebalance(toAccountEntity.getActivebalance() + transferDetails.getTransferAmount());
					if(LocalDate.now().getDayOfMonth() == 1 & toAccountEntity.getActivebalance() >0) {
						toAccountEntity.setActivebalance(1.06*(toAccountEntity.getActivebalance()));				
					}
					
					if(LocalDate.now().getDayOfMonth() == 14 | LocalDate.now().getDayOfMonth() == 28 & toAccountEntity.getActivebalance() >0) {
						toAccountEntity.setActivebalance(1.03*(toAccountEntity.getActivebalance()));				
					}
					
					toAccountEntity.setUpdateDatetime(LocalDate.now());
					accountEntities.add(toAccountEntity);
					
					accountRepository.saveAll(accountEntities);
					
					// Create transaction for FROM Account
					Transaction fromTransaction = bankingServiceHelper.createTransaction(transferDetails, fromAccountEntity.getAccountNumber(), "DEBIT");
					transactionRepository.save(fromTransaction);
					
					// Create transaction for TO Account
					Transaction toTransaction = bankingServiceHelper.createTransaction(transferDetails, toAccountEntity.getAccountNumber(), "CREDIT");
					transactionRepository.save(toTransaction);
				}

				return ResponseEntity.status(HttpStatus.OK).body("Success: Amount transferred for Customer Number " + customerNumber);
			}
				
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Number " + customerNumber + " not found.");
		}
		
	}

	/**
	 * Get all transactions for a specific account
	 * 
	 * @param accountnumber
	 * @return
	 */
	public List<TransactionDetails> findTransactionsByAccountNumber(Long accountnumber) {
		List<TransactionDetails> transactionDetails = new ArrayList<>();
		Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountnumber);
		if(accountEntityOpt.isPresent()) {
			Optional<List<Transaction>> transactionEntitiesOpt = transactionRepository.findByAccountNumber(accountnumber);
			if(transactionEntitiesOpt.isPresent()) {
				transactionEntitiesOpt.get().forEach(transaction -> {
					transactionDetails.add(bankingServiceHelper.convertToTransactionDomain(transaction));
				});
			}
		}
		
		return transactionDetails;
	}


}
