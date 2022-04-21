package rs.ac.ni.pmf.web2.issues.model.api;

import lombok.Value;

@Value
public class TicketDTO
{
	Integer id;
	String username;
	String title;
	String description;
}
