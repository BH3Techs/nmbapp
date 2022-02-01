package zw.co.nmbz.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    @Column(name="CUST_ID")
    private UUID id;
    
    private String firstName;
    
    private String surname;
    
    private String nationalid;
    
    private String title;
    
    private String homeowner;
    
    private String email;
    
	private LocalDate creationdate;
    
    private String gender;
    
    private String username;
    
    private Long customerNumber;
	
}
