package rs.ac.ni.pmf.web2.issues.model.entity.projection;

import java.time.LocalDateTime;
import lombok.Value;

@Value
public class TicketInfo
{
	String firstName;
	String lastName;
	String ticketTitle;
	LocalDateTime ticketCreationTime;
}
