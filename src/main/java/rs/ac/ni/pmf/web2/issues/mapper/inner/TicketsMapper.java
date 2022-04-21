package rs.ac.ni.pmf.web2.issues.mapper.inner;

import java.util.List;
import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.web2.issues.model.entity.TicketEntity;
import rs.ac.ni.pmf.web2.issues.model.inner.Ticket;

@Component
public class TicketsMapper
{
	public Ticket fromEntity(final TicketEntity ticketEntity)
	{
		return Ticket.builder()
			.id(ticketEntity.getId())
			.username(ticketEntity.getUsername())
			.title(ticketEntity.getTitle())
			.description(String.join("#", ticketEntity.getDescription()))
			.build();
	}

	public TicketEntity toEntity(final Ticket ticket)
	{
		return TicketEntity.builder()
			.id(ticket.getId())
			.username(ticket.getUsername())
			.title(ticket.getTitle())
			.description(List.of(ticket.getDescription().split("#")))
			.build();
	}
}
