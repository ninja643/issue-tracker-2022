package rs.ac.ni.pmf.web2.issues.model.api;

import lombok.Builder;
import lombok.Value;
import rs.ac.ni.pmf.web2.issues.exception.ErrorCode;

@Value
@Builder
public class ErrorDTO
{
	ErrorCode errorCode;
	String message;
}
