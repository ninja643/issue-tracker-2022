package rs.ac.ni.pmf.web2.issues.model.entity;

import java.util.Objects;
import javax.persistence.*;
import org.hibernate.Hibernate;
import lombok.*;

@Entity
@Table(name = "companies")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CompanyEntity
{
	@Id
	private Integer id;

	private String name;
	private String address;
	private String phone;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "firstName", column = @Column(name = "contact_first_name")),
		@AttributeOverride(name = "lastName", column = @Column(name = "contact_last_name")),
		@AttributeOverride(name = "phone", column = @Column(name = "contact_phone"))
	})
	private Person contactPerson;

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
		final CompanyEntity that = (CompanyEntity)o;
		return id != null && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode()
	{
		return getClass().hashCode();
	}
}
