package rs.ac.ni.pmf.web2.issues.model.inner;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import rs.ac.ni.pmf.web2.issues.model.Gender;

@Value
@Builder
public class User
{
	String username;
	String firstName;
	String lastName;
	Gender gender;

	@Builder.Default
	List<Ticket> createdTickets = new ArrayList<>();
}
