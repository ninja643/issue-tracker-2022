package rs.ac.ni.pmf.web2.issues.exception;

public class DuplicateTicketException extends RuntimeException
{
	public DuplicateTicketException(int id)
	{
		super("Ticket with id '" + id + "' already exists");
	}
}
