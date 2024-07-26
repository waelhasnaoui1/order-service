package com.ecommerceMicro.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "PRODUCT", url="http://localhost:8084/api/product")
public interface ProductClient {

    @GetMapping("/reduceQuantity")
    void reduceQuantity(@RequestParam("productId") String productId,
                        @RequestParam("quantity") long quantity);

}
