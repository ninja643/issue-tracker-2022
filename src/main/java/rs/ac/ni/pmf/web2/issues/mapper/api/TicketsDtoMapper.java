package rs.ac.ni.pmf.web2.issues.mapper.api;

import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.web2.issues.model.api.TicketDTO;
import rs.ac.ni.pmf.web2.issues.model.inner.Ticket;

@Component
public class TicketsDtoMapper
{
	public TicketDTO toDto(final Ticket ticket)
	{
		return new TicketDTO(ticket.getId(), ticket.getUsername(), ticket.getTitle(), ticket.getDescription());
	}

	public Ticket fromDto(final TicketDTO ticketDTO)
	{
		return Ticket.builder()
			.id(ticketDTO.getId())
			.title(ticketDTO.getTitle())
			.description(ticketDTO.getDescription())
			.username(ticketDTO.getUsername())
			.build();
	}
}
