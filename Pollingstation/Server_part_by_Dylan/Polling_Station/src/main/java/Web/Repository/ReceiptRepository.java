package Web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Web.Domain.Receipt;

@Repository
public interface ReceiptRepository extends CrudRepository<Receipt,Integer> {

}
