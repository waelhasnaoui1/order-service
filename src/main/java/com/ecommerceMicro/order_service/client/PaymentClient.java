package com.ecommerceMicro.order_service.client;

import com.ecommerceMicro.order_service.dto.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "PAYMENT-SERVICE", url="http://localhost:8083/payment")
public interface PaymentClient {

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

}
