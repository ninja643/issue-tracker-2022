package rs.ac.ni.pmf.web2.issues.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.Hibernate;
import lombok.*;
import rs.ac.ni.pmf.web2.issues.model.Gender;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserEntity
{
	@Id
	String username;

	String firstName;

	String lastName;

	LocalDate dob;

	@Enumerated(EnumType.STRING)
	Gender gender;

	@Transient
	int age;

	@Transient
	String fullName;

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
		final UserEntity that = (UserEntity)o;
		return username != null && Objects.equals(username, that.username);
	}

	@Override
	public int hashCode()
	{
		return getClass().hashCode();
	}

	@PostLoad
	public void updateTransientFields()
	{
		this.fullName = this.firstName + " " + this.lastName;
	}
}
