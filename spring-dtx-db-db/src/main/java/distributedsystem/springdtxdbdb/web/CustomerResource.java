package distributedsystem.springdtxdbdb.web;

import distributedsystem.springdtxdbdb.domain.Order;
import distributedsystem.springdtxdbdb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {
    @Autowired
    private CustomerService customerService;
    @PostMapping("order")
    public  void create(@RequestBody Order order){
     customerService.createOrder(order);
    }
    @GetMapping("/{id}")
    public Map userInfo(@PathVariable Long id){
        return  customerService.userInfo(id);
    }
}
