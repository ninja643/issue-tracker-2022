package rs.ac.ni.pmf.web2.issues.repository;

import java.util.*;
import rs.ac.ni.pmf.web2.issues.exception.TicketNotFoundException;
import rs.ac.ni.pmf.web2.issues.model.inner.Ticket;

public class TicketsRepositoryStub //implements TicketsRepository
{
	private final List<Ticket> _tickets = new ArrayList<>();

//	@Override
	public List<Ticket> findAll()
	{
		return _tickets;
	}

//	@Override
	public Optional<Ticket> findById(int id)
	{
		return _tickets.stream()
			.filter(ticket -> ticket.getId() == id)
			.findFirst();
	}

//	@Override
	public Ticket save(Ticket newTicket)
	{
		return findById(newTicket.getId())
			.map(ticket -> updateTicket(newTicket, ticket))
			.orElseGet(() -> createTicket(newTicket));
	}

//	@Override
	public void deleteById(int id)
	{
		findById(id)
			.map(_tickets::remove)
			.orElseThrow(() -> new TicketNotFoundException(id));
	}

	private Ticket updateTicket(final Ticket newTicket, final Ticket existingTicket)
	{
		_tickets.remove(existingTicket);
		_tickets.add(newTicket);
		return newTicket;
	}

	private Ticket createTicket(Ticket ticket)
	{
		_tickets.add(ticket);
		return ticket;
	}
}
