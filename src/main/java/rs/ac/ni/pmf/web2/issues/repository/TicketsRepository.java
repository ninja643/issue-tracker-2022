package rs.ac.ni.pmf.web2.issues.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.ni.pmf.web2.issues.model.entity.TicketEntity;

public interface TicketsRepository extends JpaRepository<TicketEntity, Integer>
{
}
