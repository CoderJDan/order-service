package com.example.orderservice.service;

import com.example.orderservice.dao.OrderDAO;
import com.example.orderservice.domain.*;
import com.example.orderservice.service.producer.KafkaJsonProducer;
import com.example.orderservice.service.producer.OrderSpringProducer;
import com.example.orderservice.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderDAO orderDAO;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    private final KafkaJsonProducer kafkaJsonProducer;
    private final OrderSpringProducer orderSpringProducer;

    @Override
    @Transactional // 작업이 하나만 실패해도 롤백
    public void save(OrderRequestDTO orderRequestDTO) {
        // 주문한 상품들에 대한 정보와 주문 일반 내용을 테이블에 저장할 수 있도록 작업
        // DTO -> ENTITY
        log.info("주문내역(Service) ===> {}",orderRequestDTO);

        // DTO 를 Entity 로 변환하는 작업을 Stream 으로 진행, OrderRequestDTO 에 저장된 상품리스트 DTO 를 엔티티로 변환
        List<OrderProductEntity> datadetaillist = orderRequestDTO.getOrderDetail().stream()
                                                        .map((detaildto) -> {
                                                            return modelMapper.map(detaildto, OrderProductEntity.class);
                                                        }).collect(Collectors.toList());
        log.info("{}",datadetaillist);

        // 주문 생성
        // 양방향 관계인 경우 부모 테이블과 자식 테이블의 데이터를 한 번에 저장할 수 있다.
        // 부모 테이블에 레코드를 저장할 때 자식 테이블의 레코드를 한 번에 같이 진행할 수 있다.
        OrderEntity orderEntity = OrderEntity.makeOrderEntity(orderRequestDTO.getAddr(),
                orderRequestDTO.getCustomerId(),datadetaillist);
        orderDAO.save(orderEntity);

        log.info("{}",orderRequestDTO.getOrderDetail());

        // 주문이 성공하면 주문한 정보를 product-service 로 보내기
        // 주문 정보를 하나씩 꺼내서 넘기기
//        List<ChatMessage> messages = orderRequestDTO.getOrderDetail().stream()
//                        .map((detaildto) -> {
//                            return modelMapper.map(detaildto, ChatMessage.class);
//                        }).collect(Collectors.toList());
//        log.info("{}",messages);
//
//        for(ChatMessage message : messages){
//            kafkaJsonProducer.send(message);
//        }

        // 주문 정보를 한꺼번에 넘기기
        for(OrderDetailDTO detailDTO : orderRequestDTO.getOrderDetail()){
            log.info("주문성공한 상품 => {}",detailDTO);
            orderSpringProducer.sendOrder(detailDTO);
        }
    }

    @Override
    public OrderResponseDTO findById(Long orderId) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> findAllByCustomerId(Long customerId) {
        return List.of();
    }
}
