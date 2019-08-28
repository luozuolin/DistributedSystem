package distributedsystem.springtxjta.service;


import distributedsystem.springtxjta.dao.CustomerRepository;
import distributedsystem.springtxjta.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class CustomerServiceInCode {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    /***
     * 只有传播属性为PROPAGATION_NOT_SUPPORTED时，抛出异常后数据库里面的数据没有回滚
     * @param customer
     * @return
     */
    @Transactional
    public Customer create(Customer customer){
        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus ts=transactionManager.getTransaction(def);
       try {
           customerRepository.save(customer);
          // simulateError();
           transactionManager.commit(ts);
           return customer;
       }catch (Exception ex){
           transactionManager.rollback(ts);
           throw  ex;
       }
    }
    private void simulateError(){
        throw new RuntimeException("some Data error..");
    }
}
