package distributedsystem.springtxjta.service;

import distributedsystem.springtxjta.dao.CustomerRepository;
import distributedsystem.springtxjta.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceInAnnotation {
    private static final Logger log= LoggerFactory.getLogger(CustomerServiceInAnnotation.class);
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer){
        return customerRepository.save(customer);
    }

    @Transactional
    @JmsListener(destination = "customer:msg:new",containerFactory = "jmsListenerContainerFactory")
    public void create(String name){
        log.info("create user in CustomerServiceInAnnotation by JmsListener:{}",name);
        Customer customer=new Customer();
        customer.setUsername(name);
        customer.setPassword("111111");
        customer.setRole("User");
        jmsTemplate.convertAndSend("customer:msg:reply",name);
        create(customer);

    }
}
