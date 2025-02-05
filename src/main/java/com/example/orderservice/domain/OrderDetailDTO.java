package com.example.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 상품의 상세정보
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Long productNo;
    private int orderPrice;
    private int count;
}
