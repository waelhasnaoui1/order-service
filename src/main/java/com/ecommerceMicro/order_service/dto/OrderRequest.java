package com.ecommerceMicro.order_service.dto;

import com.ecommerceMicro.order_service.enums.PaymentMode;

import java.math.BigDecimal;

public record OrderRequest(Long orderId, String productId, String skuCode, BigDecimal price, Integer quantity,
                           PaymentMode paymentMode, long totalAmount) {
}
