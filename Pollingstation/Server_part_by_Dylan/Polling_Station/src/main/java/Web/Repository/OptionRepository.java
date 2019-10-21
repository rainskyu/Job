package Web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Web.Domain.Options;
@Repository
public interface OptionRepository extends CrudRepository<Options,Integer> {

}
