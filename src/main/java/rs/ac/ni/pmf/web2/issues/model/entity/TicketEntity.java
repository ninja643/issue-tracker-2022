package rs.ac.ni.pmf.web2.issues.model.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.Hibernate;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Slf4j
@EntityListeners({ TicketEntityEventsListener.class })
public class TicketEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(length = 30, nullable = false, name = "created_by")
	String username;

	String title;

	@Convert(converter = DescriptionsConverter.class)
	List<String> description;

	@Builder.Default
	@Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP")
	LocalDateTime creationTime = LocalDateTime.now();

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
		{
			return false;
		}
		final TicketEntity that = (TicketEntity)o;

		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode()
	{
		return getClass().hashCode();
	}
}
