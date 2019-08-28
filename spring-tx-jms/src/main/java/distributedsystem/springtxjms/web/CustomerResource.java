package distributedsystem.springtxjms.web;

import distributedsystem.springtxjms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    CustomerService customerService;
    @PostMapping("message1/listen")
    public  void createlisten(@RequestParam String msg){
        jmsTemplate.convertAndSend("customer:msg1:new",msg);
    }
    @PostMapping("message1/direct")
    public  void createdirect(@RequestParam String msg){
        customerService.handle(msg);
    }
    @GetMapping("message")
    public String read(){
        jmsTemplate.setReceiveTimeout(2000);
        Object object= jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(object);
    }
}
