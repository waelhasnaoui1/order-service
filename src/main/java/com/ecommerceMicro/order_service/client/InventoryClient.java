package com.ecommerceMicro.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "inventory", url="${inverntory.url}")
public interface InventoryClient {

    @RequestMapping(method = RequestMethod.GET,value = "/api/inventory")
    boolean isInStock(@RequestParam String skuCode,@RequestParam Integer quantity);

//    @RequestMapping(method = RequestMethod.PUT,value = "/api/inventory/reduce")
    @PutMapping("/api/inventory/reduce")
    void reduceInventory(@RequestParam String skuCode,@RequestParam Integer quantity);

}
