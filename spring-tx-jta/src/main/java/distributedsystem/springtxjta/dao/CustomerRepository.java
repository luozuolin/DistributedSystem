package distributedsystem.springtxjta.dao;

import distributedsystem.springtxjta.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    Customer findOneByUsername(String username);
}
