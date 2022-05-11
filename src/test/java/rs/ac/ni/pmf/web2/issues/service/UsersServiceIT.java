package rs.ac.ni.pmf.web2.issues.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import rs.ac.ni.pmf.web2.issues.mapper.inner.TicketsMapper;
import rs.ac.ni.pmf.web2.issues.mapper.inner.UsersMapper;
import rs.ac.ni.pmf.web2.issues.model.Gender;
import rs.ac.ni.pmf.web2.issues.model.inner.User;

@DataJpaTest
@Import({ UsersService.class, UsersMapper.class, TicketsMapper.class })
class UsersServiceIT
{
	@Autowired
	private UsersService _usersService;

	@BeforeEach
	public void initializeData()
	{
		final User user = User.builder()
			.firstName("Petar")
			.lastName("Petrovic")
			.username("pera")
			.gender(Gender.MALE)
			.build();

		_usersService.createUser(user);
	}

	@Test
	public void shouldGetUser()
	{
		final User user = _usersService.findByUsername("pera");
		assertThat(user.getUsername()).isEqualTo("pera");

		assertThatThrownBy(() -> _usersService.findByUsername("pera1"))
			.isInstanceOf(RuntimeException.class)
			.hasMessage("User pera1 not found");
	}
}
