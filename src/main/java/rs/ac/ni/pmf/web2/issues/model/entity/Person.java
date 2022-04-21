package rs.ac.ni.pmf.web2.issues.model.entity;

import javax.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Person
{
	private String firstName;
	private String lastName;
	private String phone;
}

