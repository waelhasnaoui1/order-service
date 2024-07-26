package com.ecommerceMicro.order_service.dto;

import com.ecommerceMicro.order_service.enums.PaymentMode;

import java.time.Instant;

public class PaymentResponse {
    private long paymentId;
    private String status;
    private PaymentMode paymentMode;
    private long amount;
    private Instant paymentDate;
    private long orderId;
}
