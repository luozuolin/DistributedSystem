package distributedsystem.springtxjtanew.service;

import distributedsystem.springtxjtanew.dao.CustomerRepository;
import distributedsystem.springtxjtanew.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceInAnnotation {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer){
        return customerRepository.save(customer);
    }
}
