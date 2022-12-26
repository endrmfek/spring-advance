package com.spring.advanced.proxy.app.v3;


import com.spring.advanced.proxy.app.v2.OrderServiceV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/proxy")
public class OrderControllerV3 {
    private OrderServiceV2 orderService;

    public OrderControllerV3(OrderServiceV2 orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v3/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v3/no-log")
    public String noLog() {
        return "ok";
    }
}
