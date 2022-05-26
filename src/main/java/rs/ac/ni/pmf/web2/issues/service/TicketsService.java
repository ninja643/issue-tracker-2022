package rs.ac.ni.pmf.web2.issues.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rs.ac.ni.pmf.web2.issues.exception.DuplicateTicketException;
import rs.ac.ni.pmf.web2.issues.exception.TicketNotFoundException;
import rs.ac.ni.pmf.web2.issues.mapper.inner.TicketsMapper;
import rs.ac.ni.pmf.web2.issues.model.entity.TicketEntity;
import rs.ac.ni.pmf.web2.issues.model.entity.UserEntity;
import rs.ac.ni.pmf.web2.issues.model.inner.Ticket;
import rs.ac.ni.pmf.web2.issues.repository.TicketsRepository;
import rs.ac.ni.pmf.web2.issues.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketsService
{
	private final TicketsRepository _ticketsRepository;
	private final UsersRepository _usersRepository;

	private final TicketsMapper _ticketsMapper;

	public List<Ticket> getAll()
	{
		return _ticketsRepository.findAll().stream()
			.map(_ticketsMapper::fromEntity)
			.collect(Collectors.toList());
	}

	public Ticket getById(int id)
	{
		return _ticketsRepository.findById(id)
			.map(_ticketsMapper::fromEntity)
			.orElseThrow(() -> new TicketNotFoundException(id));
	}

	public Ticket createTicket(final Ticket ticket)
	{
		final Integer id = ticket.getId();
		if (id != null && _ticketsRepository.findById(id).isPresent())
		{
			throw new DuplicateTicketException(id);
		}

		final String username = ticket.getUsername();
		final UserEntity userEntity = _usersRepository.findById(username)
			.orElse(_usersRepository.save(UserEntity.builder().username(username).build()));

		final TicketEntity entity = _ticketsMapper.toEntity(ticket, userEntity);

		final TicketEntity savedTicket = _ticketsRepository.save(entity);

		return _ticketsMapper.fromEntity(savedTicket);
	}

	public Ticket updateTicket(final Ticket ticket)
	{
		final Integer id = ticket.getId();
		if (_ticketsRepository.findById(id).isEmpty())
		{
			throw new TicketNotFoundException(id);
		}

		final String username = ticket.getUsername();
		final UserEntity userEntity = _usersRepository.findById(username)
			.orElseThrow(() -> new RuntimeException("User " + username + " does not exist"));

		final TicketEntity savedTicket = _ticketsRepository.save(_ticketsMapper.toEntity(ticket, userEntity));
		return _ticketsMapper.fromEntity(savedTicket);
	}

	public void deleteTicket(int id)
	{
		if (_ticketsRepository.existsById(id))
		{
			_ticketsRepository.deleteById(id);
		}
		else
		{
			throw new TicketNotFoundException(id);
		}
	}
}
