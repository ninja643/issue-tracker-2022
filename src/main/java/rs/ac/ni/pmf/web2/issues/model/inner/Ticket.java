package rs.ac.ni.pmf.web2.issues.model.inner;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Ticket
{
	Integer id;
	String username;
	String title;
	String description;
}
