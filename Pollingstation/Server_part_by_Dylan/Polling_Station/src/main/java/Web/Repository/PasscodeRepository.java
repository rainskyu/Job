package Web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Web.Domain.Passcode;

@Repository
public interface PasscodeRepository extends CrudRepository<Passcode,Integer> {

}
