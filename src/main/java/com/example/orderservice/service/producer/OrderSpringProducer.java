package com.example.orderservice.service.producer;

import com.example.orderservice.domain.OrderDetailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// OrderDetailDTO 객체를 product-service 로 보내기
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderSpringProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public void sendOrder(OrderDetailDTO orderProductInfo) {
        // 매개변수로 전달 받은 DTO => String 으로 변환
        String orderStr = "";
        // 객체를 읽어서 스트링으로 변환해서 넘겨주는 것
        try {
            // 자바객체 -> JSON 문자열
            orderStr = objectMapper.writeValueAsString(orderProductInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("Sending order product to order service: " + orderStr);
        // publish - 메시지 전송
        kafkaTemplate.send("dev.shop.order.create2", orderStr);
    }
}
