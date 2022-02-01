package zw.co.nmbz.domain;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zw.co.nmbz.model.Branch;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountInformation {

	private Long accountnumber;
	
	private LocalDate dateOfCreation;
	
	private String currency;
	
	private String mobileNumber;
	
	private BranchInfomartion branchInfomartion;
	
	private Double initialBalance;
	
	private Double activeBalance;
	
	private Double debitAmount;
	
	private Double creditAmount;
	
	private String debitNarration;
	
	private String creditNarration;

	
	
}
