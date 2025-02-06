package com.example.orderservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "order-service 실행!!!!!!!!!!!!@@@@@@@@@";
    }

    @GetMapping("/test2")
    public String test2(@RequestHeader("RequestOrder") String data) {
        log.info("order-service 요청 성공 헤더 값 추출 : "+data);
        return "order-service config 로 실행";
    }
}
