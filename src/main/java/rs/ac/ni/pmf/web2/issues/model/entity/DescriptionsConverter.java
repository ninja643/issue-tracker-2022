package rs.ac.ni.pmf.web2.issues.model.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DescriptionsConverter implements AttributeConverter<List<String>, String>
{
	private static final String SEPARATOR = "#";

	@Override
	public String convertToDatabaseColumn(List<String> descriptions)
	{
		return String.join(SEPARATOR, descriptions);
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData)
	{
		return Arrays.stream(dbData.split(SEPARATOR)).collect(Collectors.toList());
	}
}
