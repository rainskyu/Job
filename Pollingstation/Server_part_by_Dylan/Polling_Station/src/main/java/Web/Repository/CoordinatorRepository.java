package Web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Web.Domain.Coordinator;


@Repository
public interface CoordinatorRepository extends CrudRepository<Coordinator,Integer>{
	
	//public Coordinator findByUsername(String username);

}
