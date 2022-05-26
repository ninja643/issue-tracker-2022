package rs.ac.ni.pmf.web2.issues.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import rs.ac.ni.pmf.web2.issues.model.Gender;
import rs.ac.ni.pmf.web2.issues.model.entity.TicketEntity;
import rs.ac.ni.pmf.web2.issues.model.entity.UserEntity;
import rs.ac.ni.pmf.web2.issues.model.entity.projection.ITicketInfo;
import rs.ac.ni.pmf.web2.issues.model.entity.projection.TicketCountPerUser;
import rs.ac.ni.pmf.web2.issues.repository.specification.UsersByTicketCountSpecification;

@DataJpaTest
class UsersRepositoryTest
{
	@Autowired
	private UsersRepository _usersRepository;

	@BeforeEach
	public void initializeData()
	{
		final UserEntity user = UserEntity.builder()
			.username("pera")
			.age(20)
			.firstName("Petar")
			.lastName("Petrovic")
			.gender(Gender.MALE)
			.build();
		user.addCreatedTicket(
			TicketEntity.builder().title("Test Ticket 1").creationTime(LocalDateTime.now()).build(),
			TicketEntity.builder().title("Test Ticket 2").creationTime(LocalDateTime.now()).build(),
			TicketEntity.builder().title("Test Ticket 3").creationTime(LocalDateTime.now()).build(),
			TicketEntity.builder().title("Test Ticket 4").creationTime(LocalDateTime.now()).build(),
			TicketEntity.builder().title("Test Ticket 5").creationTime(LocalDateTime.now()).build());

		_usersRepository.save(user);

		final UserEntity user2 = UserEntity.builder()
			.username("mika")
			.age(30)
			.firstName("Mika")
			.lastName("Petrovic")
			.gender(Gender.MALE)
			.build();
		user2.addCreatedTicket(
			TicketEntity.builder().title("Test Ticket 6").creationTime(LocalDateTime.now()).build(),
			TicketEntity.builder().title("Test Ticket 7").creationTime(LocalDateTime.now()).build(),
			TicketEntity.builder().title("Test Ticket 8").creationTime(LocalDateTime.now()).build());
		_usersRepository.save(user2);

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

//	@Test
//	public void shouldExecuteModifyingQueries()
//	{
//		assertThat(_usersRepository.findByFirstNameIgnoreCase("Petar")).hasSize(1);
//		assertThat(_usersRepository.findByFirstNameIgnoreCase("Milorad")).hasSize(0);
//
//		final int updatedCnt = _usersRepository.updateFirstName("pera", "Milorad");
//		assertThat(updatedCnt).isEqualTo(1);
//
//		assertThat(_usersRepository.findByFirstNameIgnoreCase("Petar")).hasSize(0);
//		assertThat(_usersRepository.findByFirstNameIgnoreCase("Milorad")).hasSize(1);
//
//		int deletedCnt = _usersRepository.deleteByUsername("mika");
//		assertThat(deletedCnt).isEqualTo(1);
//		assertThat(_usersRepository.findAll()).hasSize(3);
//
//		deletedCnt = _usersRepository.deleteByUsername("mika423421");
//		assertThat(deletedCnt).isEqualTo(0);
//	}

	@Test
	public void shouldRetrieveDifferentResultTypes()
	{
		//_usersRepository.findAll().stream().map(UserEntity::getUsername).forEach(System.out::println);
		//_usersRepository.getAllUsernames().forEach(System.out::println);
		//		assertThat(_usersRepository.getAllUsernames()).containsExactlyInAnyOrder("mika", "mira", "mira2", "pera");

//		final List<Object[]> infos = _usersRepository.getTicketsInfosAsObjectArrays();
//
//		assertThat(infos).hasSize(8);

//		final List<TicketInfo> infos = _usersRepository.getTicketInfos();
//		assertThat(infos).hasSize(8);

//		infos.forEach(System.out::println);

		final List<ITicketInfo> infos = _usersRepository.getITicketInfos();
		assertThat(infos).hasSize(8);
		infos.forEach(info -> {
			System.out.format("[%s, %s, %s, %s]%n", info.getFirstName(), info.getLastName(), info.getTicketTitle(), info.getTicketCreationTime());
		});
	}

	@Test
	public void shouldGetTicketCountsByUsers()
	{
		final List<TicketCountPerUser> counts = _usersRepository.getTicketCounts();

		assertThat(counts).hasSize(4);
		assertThat(counts.get(0).getUsername()).isEqualTo("mika");
		assertThat(counts.get(0).getTicketsCount()).isEqualTo(3);

		assertThat(counts.get(1).getUsername()).isEqualTo("mira");
		assertThat(counts.get(1).getTicketsCount()).isEqualTo(0);

		assertThat(counts.get(2).getUsername()).isEqualTo("mira2");
		assertThat(counts.get(2).getTicketsCount()).isEqualTo(0);

		assertThat(counts.get(3).getUsername()).isEqualTo("pera");
		assertThat(counts.get(3).getTicketsCount()).isEqualTo(5);
	}

	@Test
	public void shouldExecuteSpecification()
	{
//		final Specification<UserEntity> specification = new UsersByTicketCountSpecification("Petar", "Petrovic");
		final Specification<UserEntity> specification = new UsersByTicketCountSpecification("TICKET");
		List<UserEntity> users = _usersRepository.findAll(specification);
		assertThat(users).hasSize(2);
		users.forEach(System.out::println);
	}
}
