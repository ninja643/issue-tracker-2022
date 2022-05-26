package rs.ac.ni.pmf.web2.issues.mapper.inner;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.web2.issues.model.entity.TicketEntity;
import rs.ac.ni.pmf.web2.issues.model.entity.UserEntity;
import rs.ac.ni.pmf.web2.issues.model.inner.Ticket;
import rs.ac.ni.pmf.web2.issues.repository.UsersRepository;

@Component
public class TicketsMapper
{
	public Ticket fromEntity(final TicketEntity ticketEntity)
	{
		return Ticket.builder()
			.id(ticketEntity.getId())
			.username(ticketEntity.getCreatedBy().getUsername())
			.title(ticketEntity.getTitle())
			.description(String.join("#", ticketEntity.getDescription()))
			.build();
	}

	public TicketEntity toEntity(final Ticket ticket, final UserEntity userEntity)
	{
		return TicketEntity.builder()
			.id(ticket.getId())
			.createdBy(userEntity)
			.title(ticket.getTitle())
			.description(List.of(ticket.getDescription().split("#")))
			.build();
	}
}
