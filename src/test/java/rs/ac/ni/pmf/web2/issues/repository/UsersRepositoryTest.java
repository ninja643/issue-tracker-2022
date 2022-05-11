package rs.ac.ni.pmf.web2.issues.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import rs.ac.ni.pmf.web2.issues.model.Gender;
import rs.ac.ni.pmf.web2.issues.model.entity.TicketEntity;
import rs.ac.ni.pmf.web2.issues.model.entity.UserEntity;

@DataJpaTest
class UsersRepositoryTest
{
	@Autowired
	private UsersRepository _usersRepository;

	@BeforeEach
	public void initializeData()
	{
		final TicketEntity ticket = TicketEntity.builder()
			.title("Test Ticket")
			.creationTime(LocalDateTime.now())
			.build();

		final UserEntity user = UserEntity.builder()
			.username("pera")
			.age(20)
			.firstName("Petar")
			.lastName("Petrovic")
			.gender(Gender.MALE)
			.build();
		user.addCreatedTicket(ticket);

		_usersRepository.save(user);

		_usersRepository.save(UserEntity.builder()
			.username("mika")
			.age(30)
			.firstName("Mika")
			.lastName("Petrovic")
			.gender(Gender.MALE)
			.build());

		_usersRepository.save(UserEntity.builder()
			.username("mira")
			.age(25)
			.firstName("Mira")
			.lastName("Petrovic")
			.gender(Gender.FEMALE)
			.build());

		_usersRepository.save(UserEntity.builder()
			.username("mira2")
			.age(25)
			.firstName("Mira")
			.lastName("Mirkovic")
			.gender(Gender.FEMALE)
			.build());
	}

	@Test
	public void shouldExecuteQueries()
	{
		assertThat(_usersRepository.findAll()).hasSize(4);
		assertThat(_usersRepository.findById("mira")).isPresent();
		assertThat(_usersRepository.findById("miraaaa")).isEmpty();

		assertThat(_usersRepository.findByLastName("Petrovic")).hasSize(3);
		assertThat(_usersRepository.findByFirstNameAndLastName("Mira", "Petrovic")).hasSize(1);
		assertThat(_usersRepository.findByFirstNameOrLastName("Mira", "Petrovic")).hasSize(4);

		assertThat(_usersRepository.findByFirstNameIgnoreCase("mira")).hasSize(2);
	}

	@Test
	public void shouldExecuteMoreQueries()
	{
		final List<UserEntity> usersAsc = _usersRepository.findByFirstNameOrderByLastNameAsc("Mira");
		assertThat(usersAsc.get(0).getLastName()).isEqualTo("Mirkovic");

		final List<UserEntity> usersDesc = _usersRepository.findByFirstNameOrderByLastNameDesc("Mira");
		assertThat(usersDesc.get(0).getLastName()).isEqualTo("Petrovic");

		assertThat(_usersRepository.findByUsernameLike("mi%")).hasSize(3);

		assertThat(_usersRepository.findByGender(Gender.FEMALE)).hasSize(2);
	}

	@Test
	public void shoudExecuteCustomQueries()
	{
		UserEntity user = _usersRepository.getOneUser("mika");
		assertThat(user).isNotNull();

		user = _usersRepository.getOneUser("mika321");
		assertThat(user).isNull();

		List<UserEntity> users = _usersRepository.getSomeUsers("mi");
		assertThat(users).hasSize(3);
//		users.forEach(System.out::println);
	}

	@Test
	public void shouldExecuteModifyingQueries()
	{
		assertThat(_usersRepository.findByFirstNameIgnoreCase("Petar")).hasSize(1);
		assertThat(_usersRepository.findByFirstNameIgnoreCase("Milorad")).hasSize(0);

		final int updatedCnt = _usersRepository.updateFirstName("pera", "Milorad");
		assertThat(updatedCnt).isEqualTo(1);

		assertThat(_usersRepository.findByFirstNameIgnoreCase("Petar")).hasSize(0);
		assertThat(_usersRepository.findByFirstNameIgnoreCase("Milorad")).hasSize(1);

		int deletedCnt = _usersRepository.deleteByUsername("mika");
		assertThat(deletedCnt).isEqualTo(1);
		assertThat(_usersRepository.findAll()).hasSize(3);

		deletedCnt = _usersRepository.deleteByUsername("mika423421");
		assertThat(deletedCnt).isEqualTo(0);
	}
}
