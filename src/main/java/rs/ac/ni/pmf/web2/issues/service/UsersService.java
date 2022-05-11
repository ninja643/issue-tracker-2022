package rs.ac.ni.pmf.web2.issues.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.web2.issues.mapper.inner.UsersMapper;
import rs.ac.ni.pmf.web2.issues.model.entity.UserEntity;
import rs.ac.ni.pmf.web2.issues.model.inner.User;
import rs.ac.ni.pmf.web2.issues.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class UsersService
{
	private final UsersRepository _usersRepository;
	private final UsersMapper _usersMapper;

	public String createUser(final User user)
	{
		final UserEntity savedUser = _usersRepository.save(_usersMapper.toEntity(user));
		return savedUser.getUsername();
	}

	public User findByUsername(final String username)
	{
		final UserEntity userEntity =
			_usersRepository.findById(username).orElseThrow(() -> new RuntimeException("User " + username + " not found"));
		return _usersMapper.fromEntity(userEntity);
	}
}
