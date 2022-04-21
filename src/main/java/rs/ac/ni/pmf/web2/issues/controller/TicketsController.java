package rs.ac.ni.pmf.web2.issues.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.web2.issues.mapper.api.TicketsDtoMapper;
import rs.ac.ni.pmf.web2.issues.model.api.TicketDTO;
import rs.ac.ni.pmf.web2.issues.model.inner.Ticket;
import rs.ac.ni.pmf.web2.issues.service.TicketsService;

@RestController
@RequiredArgsConstructor
public class TicketsController
{
	private final TicketsService _ticketsService;

	private final TicketsDtoMapper _ticketsDtoMapper;

	@GetMapping("/tickets")
	public List<TicketDTO> getTicket()
	{
		return _ticketsService.getAll()
			.stream().map(_ticketsDtoMapper::toDto)
			.collect(Collectors.toList());
	}

	@GetMapping("/tickets/{id}")
	public TicketDTO getTicket(@PathVariable(value = "id") int id)
	{
		return _ticketsDtoMapper.toDto(_ticketsService.getById(id));
	}

	@PostMapping("/tickets")
	@ResponseStatus(HttpStatus.CREATED)
	public TicketDTO createTicket(@RequestBody TicketDTO ticketDTO)
	{
		final Ticket ticket = _ticketsDtoMapper.fromDto(ticketDTO);

		return _ticketsDtoMapper.toDto(_ticketsService.createTicket(ticket));
	}

	@PutMapping("/tickets/{id}")
	public TicketDTO updateTicket(@RequestBody TicketDTO ticketDTO, @PathVariable(value = "id") int id)
	{
		if (ticketDTO.getId() != id)
		{
			throw new IllegalArgumentException("ID of a ticket cannot be updated!");
		}

		final Ticket ticket = _ticketsDtoMapper.fromDto(ticketDTO);
		return _ticketsDtoMapper.toDto(_ticketsService.updateTicket(ticket));
	}

	@DeleteMapping("/tickets/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTicket(@PathVariable(value = "id") int id)
	{
		_ticketsService.deleteTicket(id);
	}
}
