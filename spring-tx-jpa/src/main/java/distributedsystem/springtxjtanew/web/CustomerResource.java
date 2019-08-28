package distributedsystem.springtxjtanew.web;

import distributedsystem.springtxjtanew.dao.CustomerRepository;
import distributedsystem.springtxjtanew.domain.Customer;
import distributedsystem.springtxjtanew.service.CustomerServiceInAnnotation;
import distributedsystem.springtxjtanew.service.CustomerServiceInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    @Autowired
    CustomerServiceInCode customerServiceInCode;
    @Autowired
    CustomerServiceInAnnotation customerServiceInAnnotation;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/code")
    public Customer createInCode(@RequestBody  Customer customer){
     return    customerServiceInCode.create(customer);
    }
    @PostMapping("/annotation")
    public Customer createInAnnotation(@RequestBody  Customer customer){
        return    customerServiceInAnnotation.create(customer);
    }
    @GetMapping("")
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }
}
