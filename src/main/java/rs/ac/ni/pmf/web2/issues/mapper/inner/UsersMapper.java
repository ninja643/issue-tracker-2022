package rs.ac.ni.pmf.web2.issues.mapper.inner;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.web2.issues.model.entity.UserEntity;
import rs.ac.ni.pmf.web2.issues.model.inner.User;

@Component
@RequiredArgsConstructor
public class UsersMapper
{
	private final TicketsMapper _ticketsMapper;

	public UserEntity toEntity(final User user)
	{
		final UserEntity userEntity = UserEntity.builder()
			.username(user.getUsername())
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.gender(user.getGender())
			.build();

		user.getCreatedTickets().forEach(ticket -> userEntity.addCreatedTicket(_ticketsMapper.toEntity(ticket, userEntity)));

		return userEntity;
	}

	public User fromEntity(final UserEntity userEntity)
	{
		return User.builder()
			.username(userEntity.getUsername())
			.firstName(userEntity.getFirstName())
			.lastName(userEntity.getLastName())
			.gender(userEntity.getGender())
			.build();
	}
}
