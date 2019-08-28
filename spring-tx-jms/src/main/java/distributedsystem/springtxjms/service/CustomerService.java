package distributedsystem.springtxjms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


@Service
public class CustomerService {
    private static final Logger log= LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    JmsTemplate jmsTemplate;

    /***
     * 直接调用的话抛出异常消息还会入队
     * JmsListener方法触发的话会回滚，消息不回入队
     * @param msg
     */
    @JmsListener(destination = "customer:msg1:new",containerFactory = "jmsListenerContainerFactory")
    public void handle(String msg){
        log.info("Get msg1:{}",msg);
        String reply="Reply1-"+msg;
        jmsTemplate.convertAndSend("customer:msg:reply",reply);
        if(msg.contains("error")){
            simulateError();
        }
    }
    private void simulateError(){
        throw new RuntimeException("some Data Error");
    }

}
