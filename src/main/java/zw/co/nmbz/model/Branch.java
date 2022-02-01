package zw.co.nmbz.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Branch {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BRANCH_ID")
	private UUID id;
	
	private Integer branchCode;
	
	private String branchName;
	
	private Integer routingNumber;
}
