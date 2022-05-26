package rs.ac.ni.pmf.web2.issues.model.entity.projection;

import java.time.LocalDateTime;

public interface ITicketInfo
{
	String getFirstName();
	String getLastName();
	String getTicketTitle();
	LocalDateTime getTicketCreationTime();
}
