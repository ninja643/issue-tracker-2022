package rs.ac.ni.pmf.web2.issues.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import rs.ac.ni.pmf.web2.issues.model.Gender;
import rs.ac.ni.pmf.web2.issues.model.entity.UserEntity;
import rs.ac.ni.pmf.web2.issues.model.entity.projection.*;

public interface UsersRepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity>
{
	List<UserEntity> findByLastName(String lastName);

	List<UserEntity> findByFirstNameAndLastName(String firstName, String lastName);

	List<UserEntity> findByFirstNameOrLastName(String firstName, String lastName);

	List<UserEntity> findByFirstNameIgnoreCase(String firstName);

	List<UserEntity> findByFirstNameOrderByLastNameAsc(String firstName);

	List<UserEntity> findByFirstNameOrderByLastNameDesc(String firstName);

	List<UserEntity> findByUsernameLike(String search);

	List<UserEntity> findByGender(Gender gender);

	List<UserEntity> findByFirstNameIgnoreCaseOrLastNameIgnoreCaseOrderByFirstNameDescLastNameAsc(
		String firstName,
		String lastName);

	@Query("select u from UserEntity as u where u.username = :usernameParam")
	UserEntity getOneUser(@Param("usernameParam") String username);

	@Query("select u from UserEntity as u where u.username like ?1%")
	List<UserEntity> getSomeUsers(String namePrefix);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("delete from UserEntity as u where u.username = :username")
	int deleteByUsername(@Param("username") String username);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("update UserEntity as u set u.firstName = :newFirstName where u.username = :username")
	int updateFirstName(@Param("username") String username, @Param("newFirstName") String firstName);

	@Query("select u.username from UserEntity u")
	List<String> getAllUsernames();

	// !!!!
	//	@Query(value = "select u.username from users u limit 1", nativeQuery = true)
	//	String getUsername();

	@Query("select u.firstName, u.lastName, t.title, t.creationTime "
		+ "from UserEntity u inner join TicketEntity t on t.createdBy.username = u.username")
	List<Object[]> getTicketsInfosAsObjectArrays();

	@Query(
		"select new rs.ac.ni.pmf.web2.issues.model.entity.projection.TicketInfo(u.firstName, u.lastName, t.title, t.creationTime) "
			+ "from UserEntity u inner join TicketEntity t on t.createdBy.username = u.username")
	List<TicketInfo> getTicketInfos();

	@Query("select u.firstName as firstName, u.lastName as lastName, t.title as ticketTitle, t.creationTime as ticketCreationTime "
		+ " from UserEntity u inner join TicketEntity t on t.createdBy.username = u.username")
	List<ITicketInfo> getITicketInfos();

	@Query("select u.username as username, count(t.id) as ticketsCount "
		+ "from UserEntity u left join TicketEntity t on t.createdBy.username = u.username "
		+ "group by u.username order by username")
	List<TicketCountPerUser> getTicketCounts();
}
