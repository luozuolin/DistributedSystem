package distributedsystem.springtxjtanew.service;

import distributedsystem.springtxjtanew.dao.CustomerRepository;
import distributedsystem.springtxjtanew.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceInAnnotation {
    private static final Logger log= LoggerFactory.getLogger(CustomerServiceInAnnotation.class);
    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    /***
     * 多个事务测试地址：http://localhost:8080/api/customer/annotation
     * 加入spring-boot-starter-jta-atomikos后，原来的多个事务实现了报错后同时回滚
     * @param customer
     */
    @Transactional
    public void create(Customer customer){
         customerRepository.save(customer);
        jmsTemplate.convertAndSend("customer:msg:reply",customer.toString());
    }



    @JmsListener(destination = "customer:msg:new")
    public void create(String name)  {
        log.info("create user in CustomerServiceInAnnotation by JmsListener:{}",name);
        Customer customer=new Customer();
        customer.setUsername(name);
        customer.setPassword("111111");
        customer.setRole("User");
        try {
         //   create(customer);
        }catch (Exception ex){}
        jmsTemplate.convertAndSend("customer:msg:reply",name);
      //  Thread.sleep(2000);
      //  create(customer);
        customerRepository.save(customer);

    }
}
