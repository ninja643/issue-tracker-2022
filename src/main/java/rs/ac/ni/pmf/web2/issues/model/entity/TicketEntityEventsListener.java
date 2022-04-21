package rs.ac.ni.pmf.web2.issues.model.entity;

import javax.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketEntityEventsListener
{
	@PrePersist
	@PreRemove
	@PreUpdate
	private void beforeAccess(TicketEntity ticketEntity)
	{
		if (ticketEntity.getId() == null)
		{
			log.info("Adding a new entity {}", ticketEntity);
		}
		else
		{
			log.info("Update/delete of entity {}", ticketEntity);
		}
	}

	@PostPersist
	private void afterCreate(TicketEntity ticket)
	{
		log.info("Ticket {} created", ticket);
	}
}
