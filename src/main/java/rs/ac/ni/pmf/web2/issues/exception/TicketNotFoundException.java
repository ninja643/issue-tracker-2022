package rs.ac.ni.pmf.web2.issues.exception;

public class TicketNotFoundException extends RuntimeException
{
	public TicketNotFoundException(int id)
	{
		super("Ticket with id " + id + " does not exist");
	}
}
