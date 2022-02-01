package zw.co.nmbz.domain;

import java.time.LocalDate;
import java.util.Date;

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
public class CustomerDetails {

    private String firstName;
    
    private String surname;
    
    private String nationalId;
    
    private String title;
    
    private String homeOwner;
    
    private String status;
    
    private String email;
    
    private LocalDate creationdate;
    
    private String gender;
    
    private String username;
    
    private Long customerNumber;
    
}
