package zw.co.nmbz.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BranchInfomartion {
	private String branchName;
	
	private Integer branchCode;
	
	private Integer routingNumber;

}
