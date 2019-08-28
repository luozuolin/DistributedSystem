package distributedsystem.springtxjtanew.dao;

import distributedsystem.springtxjtanew.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    Customer findOneByUsername(String username);
}
