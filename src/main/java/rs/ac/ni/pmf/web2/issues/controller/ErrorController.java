package rs.ac.ni.pmf.web2.issues.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.ac.ni.pmf.web2.issues.exception.*;
import rs.ac.ni.pmf.web2.issues.model.api.ErrorDTO;

@ControllerAdvice
@ResponseBody
public class ErrorController
{
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleIllegalArgumentException(final IllegalArgumentException e)
	{
		return ErrorDTO.builder()
			.errorCode(ErrorCode.BAD_REQUEST)
			.message(e.getMessage())
			.build();
	}

	@ExceptionHandler(DuplicateTicketException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDTO handleBadRequest(final DuplicateTicketException e)
	{
		return ErrorDTO.builder()
			.errorCode(ErrorCode.DUPLICATE_RESOURCE)
			.message(e.getMessage())
			.build();
	}

	@ExceptionHandler(TicketNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDTO handleTicketNotFoundException(final TicketNotFoundException e)
	{
		return ErrorDTO.builder()
			.errorCode(ErrorCode.RESOURCE_NOT_FOUND)
			.message(e.getMessage())
			.build();
	}
}
