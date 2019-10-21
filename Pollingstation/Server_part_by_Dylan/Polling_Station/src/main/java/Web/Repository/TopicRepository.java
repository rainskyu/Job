package Web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Web.Domain.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic,Integer> {

	
}
