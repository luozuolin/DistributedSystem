package distributedsystem.springtxjtanew.web;

import distributedsystem.springtxjtanew.dao.CustomerRepository;
import distributedsystem.springtxjtanew.domain.Customer;
import distributedsystem.springtxjtanew.service.CustomerServiceInAnnotation;
import distributedsystem.springtxjtanew.service.CustomerServiceInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    @Autowired
    JmsTemplate jmsTemplate;
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
    public void createInAnnotation(@RequestBody  Customer customer){
            customerServiceInAnnotation.create(customer);
    }
    @GetMapping("")
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    @PostMapping("message/send")
    public  void createlisten(@RequestParam String msg){
        jmsTemplate.convertAndSend("customer:msg:new",msg);
    }
    @Transactional
    @GetMapping("message")
    public String read(){
        jmsTemplate.setReceiveTimeout(2000);
        Object object= jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(object);
    }
}
