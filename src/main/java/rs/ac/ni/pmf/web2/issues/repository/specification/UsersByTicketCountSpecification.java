package rs.ac.ni.pmf.web2.issues.repository.specification;

import java.time.LocalDateTime;
import java.util.Locale;
import javax.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;
import rs.ac.ni.pmf.web2.issues.model.entity.*;

@RequiredArgsConstructor
public class UsersByTicketCountSpecification implements Specification<UserEntity>
{
	//	private final int _minIssues;
	//	private final String firstNameSearch;
	//	private final String lastNameSearch;
	private final String ticketTitleSearch;

	@Override
	public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
	{
		/*
			select * from users where firstName=? and lastName=?
		 */
		//		final Path<String> firstNamePath = root.get(UserEntity_.firstName);
		//		final Path<String> lastNamePath = root.get(UserEntity_.lastName);

		//		Predicate p1 = criteriaBuilder.equal(firstNamePath, firstNameSearch);
		//		Predicate p2 = criteriaBuilder.equal(lastNamePath, lastNameSearch);

		final Path<String> usernamePath = root.get(UserEntity_.username);
		final ListJoin<UserEntity, TicketEntity> ticketsJoin = root.join(UserEntity_.createdTickets);

		final Path<String> ticketTitlePath = ticketsJoin.get(TicketEntity_.title);
		final Path<LocalDateTime> creationTimePath = ticketsJoin.get(TicketEntity_.creationTime);

		query.groupBy(usernamePath);

		//		return criteriaBuilder.and(p1, p2);
		final Predicate likePredicate =
			criteriaBuilder.like(criteriaBuilder.lower(ticketTitlePath), "%" + ticketTitleSearch.toLowerCase(Locale.ROOT) + "%");

		final Predicate creationTimePredicate =
			criteriaBuilder.between(creationTimePath, LocalDateTime.of(2022, 3, 1, 0, 0), LocalDateTime.now());

		return criteriaBuilder.and(likePredicate, creationTimePredicate);
	}
}
