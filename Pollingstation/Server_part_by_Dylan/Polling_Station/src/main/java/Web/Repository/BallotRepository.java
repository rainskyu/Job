package Web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Web.Domain.Ballot;

@Repository
public interface BallotRepository extends CrudRepository<Ballot,Integer> {

	
}
