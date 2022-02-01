package zw.co.nmbz.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Account {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ACCT_ID")
	private UUID id;
	
	private Long accountNumber;
	
	private LocalDate dateofcreation;
	
	private String currency;
	
	private String mobilenumber;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Branch branchInfomartion;
	
	private Double initialbalance;
	
	private Double activebalance;
	
	private Double debitamount;
	
	private Double creditamount;
	
	private String debitnarration;
	
	private String creditnarration;   
	
	private LocalDate updateDatetime;
	  
}
