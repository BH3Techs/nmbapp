package zw.co.nmbz.service.helper;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import zw.co.nmbz.domain.AccountInformation;
import zw.co.nmbz.domain.BranchInfomartion;
import zw.co.nmbz.domain.CustomerDetails;
import zw.co.nmbz.domain.TransactionDetails;
import zw.co.nmbz.domain.TransferDetails;
import zw.co.nmbz.model.Account;
import zw.co.nmbz.model.Branch;
import zw.co.nmbz.model.Customer;
import zw.co.nmbz.model.Transaction;

@Component
public class BankingServiceHelper {

	public CustomerDetails convertToCustomerDomain(Customer customer) {
		
		return CustomerDetails.builder()
				.firstName(customer.getFirstName())
				.surname(customer.getSurname())
				.nationalId(customer.getNationalid())
				.title(customer.getTitle())
				.homeOwner(customer.getHomeowner())
				.email(customer.getEmail())
				.creationdate(customer.getCreationdate())
				.gender(customer.getGender())
				.username(customer.getUsername())
				.build();
	}
	
	public Customer convertToCustomerEntity(CustomerDetails customerDetails) {
		
		
		return Customer.builder()
				.firstName(customerDetails.getFirstName())
				.surname(customerDetails.getSurname())
				.nationalid(customerDetails.getNationalId())
				.title(customerDetails.getTitle())
				.homeowner(customerDetails.getHomeOwner())
				.creationdate(customerDetails.getCreationdate())
				.gender(customerDetails.getGender())
				.username(customerDetails.getUsername())
				.build();
	}

	public AccountInformation convertToAccountDomain(Account account) {

		return AccountInformation.builder()
				.accountnumber(account.getAccountNumber())
				.dateOfCreation(account.getDateofcreation())
				.currency(account.getCurrency())
				.mobileNumber(account.getMobilenumber())
				.branchInfomartion(convertToBranchDomain(account.getBranchInfomartion()))
				.initialBalance(account.getInitialbalance())
				.activeBalance(account.getActivebalance())
				.debitAmount(account.getDebitamount())
				.creditAmount(account.getCreditamount())
				.debitNarration(account.getDebitnarration())
				.creditNarration(account.getCreditnarration())
				.build();
	}
	
	public Account convertToAccountEntity(AccountInformation accInfo) {
		
		return Account.builder()
				.accountNumber(accInfo.getAccountnumber())
				.dateofcreation(accInfo.getDateOfCreation())
				.currency(accInfo.getCurrency())
				.mobilenumber(accInfo.getMobileNumber())
				.branchInfomartion(convertToBranchEntity(accInfo.getBranchInfomartion()))
				.initialbalance(accInfo.getInitialBalance())
				.activebalance(accInfo.getActiveBalance())
				.creditamount(accInfo.getCreditAmount())
				.creditamount(accInfo.getCreditAmount())
				.debitnarration(accInfo.getDebitNarration())
				.creditnarration(accInfo.getCreditNarration())
				.build();
	}
	
//	public AddressDetails convertToAddressDomain(Address address) {
//		
//		return AddressDetails.builder().address1(address.getAddress1())
//				.address2(address.getAddress2())
//				.city(address.getCity())
//				.state(address.getState())
//				.zip(address.getZip())
//				.country(address.getCountry())
//				.build();
//	}
	
//	public Address convertToAddressEntity(AddressDetails addressDetails) {
//		
//		return Address.builder().address1(addressDetails.getAddress1())
//				.address2(addressDetails.getAddress2())
//				.city(addressDetails.getCity())
//				.state(addressDetails.getState())
//				.zip(addressDetails.getZip())
//				.country(addressDetails.getCountry())
//				.build();
//	}
//	
//	public ContactDetails convertToContactDomain(Contact contact) {
//		
//		return ContactDetails.builder()
//				.emailId(contact.getEmailId())
//				.homePhone(contact.getHomePhone())
//				.workPhone(contact.getWorkPhone())
//				.build();
//	}
	
//	public Contact convertToContactEntity(ContactDetails contactDetails) {
//		
//		return Contact.builder()
//				.emailId(contactDetails.getEmailId())
//				.homePhone(contactDetails.getHomePhone())
//				.workPhone(contactDetails.getWorkPhone())
//				.build();
//	}
	
	public BranchInfomartion convertToBranchDomain(Branch branchInfo) {
		
		return BranchInfomartion.builder()
				.branchCode(branchInfo.getBranchCode())
				.branchName(branchInfo.getBranchName())
				.routingNumber(branchInfo.getRoutingNumber())
				.build();
	}
	
	public Branch convertToBranchEntity(BranchInfomartion branchInformation) {
		
		return Branch.builder()
				.branchCode(branchInformation.getBranchCode())
				.branchName(branchInformation.getBranchName())
				.routingNumber(branchInformation.getRoutingNumber())
				.build();
	}

	public TransactionDetails convertToTransactionDomain(Transaction transaction) {
		
		return TransactionDetails.builder()
									.txAmount(transaction.getTxAmount())
									.txDateTime(transaction.getTxDateTime())
									.txType(transaction.getTxType())
									.accountnumber(transaction.getAccountNumber())
									.build();
	}
	
	public Transaction convertToTransactionEntity(TransactionDetails transactionDetails) {
		
		return Transaction.builder()
							.txAmount(transactionDetails.getTxAmount())
							.txDateTime(transactionDetails.getTxDateTime())
							.txType(transactionDetails.getTxType())
							.accountNumber(transactionDetails.getAccountnumber())
							.build();
	}
	
	public Transaction createTransaction(TransferDetails transferDetails, Long accountnumber, String txType) {
		
		return Transaction.builder()
							.accountNumber(accountnumber)
							.txAmount(transferDetails.getTransferAmount())
							.txType(txType)
							.txDateTime(LocalDate.now())
							.build();
	}
}
