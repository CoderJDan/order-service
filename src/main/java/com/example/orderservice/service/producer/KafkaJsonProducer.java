package com.example.orderservice.service.producer;

import com.example.orderservice.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// 카프카에서 서버로 메시지를 전송하는 역할을 담당
@Service
@RequiredArgsConstructor
public class KafkaJsonProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message) {
//        System.out.println("send message: " + message.getOrderPrice());
//        System.out.println("send message: " + message.getProductNo());
        kafkaTemplate.send("dev.shop.order.create2",message);
    }
}
