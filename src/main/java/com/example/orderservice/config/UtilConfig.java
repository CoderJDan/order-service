package com.example.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UtilConfig {
    //외부라이브러리
    //객체를 변환하기 위해 사용
    //------------
    // dto -> entity
    // entity -> dto
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    // Jackson 라이브러리에서 제공하는 클래스
    // 자바 객체를 JSON 문자열로 변환, JSON 을 자바 객체로 변환
    // Json 문자열을 자바 객체로 변환
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}












