package com.ecommerceMicro.order_service.services;

import com.ecommerceMicro.order_service.client.InventoryClient;
import com.ecommerceMicro.order_service.client.PaymentClient;
import com.ecommerceMicro.order_service.client.ProductClient;
import com.ecommerceMicro.order_service.dto.OrderRequest;
import com.ecommerceMicro.order_service.dto.PaymentRequest;
import com.ecommerceMicro.order_service.model.Order;
import com.ecommerceMicro.order_service.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {


    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    private final ProductClient productClient;

    private final PaymentClient paymentClient;


    public long placeOrder(OrderRequest orderRequest){
        log.info("OrderService | placeOrder is called");

        log.info("OrderService | placeOrder | Placing Order Request orderRequest : {}", orderRequest.toString());
        log.info("OrderServiceImpl | placeOrder | Calling productService through FeignClient");

        productClient.reduceQuantity(orderRequest.productId(), orderRequest.quantity());
        inventoryClient.reduceInventory(orderRequest.skuCode(), orderRequest.quantity());


        log.info("OrderService | placeOrder | Creating Order with Status CREATED");

        boolean inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (inStock) {
            var order = mapToOrder(orderRequest);
            orderRepository.save(order);
            log.info("OrderService | placeOrder | Calling Payment Service to complete the payment");

            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .orderId(order.getOrderId())  // Changed to getOrderId()
                    .paymentMode(orderRequest.paymentMode())
                    .amount(orderRequest.totalAmount())
                    .build();

            String orderStatus = null;

            try {
                paymentClient.doPayment(paymentRequest);
                log.info("OrderService | placeOrder | Payment done successfully. Changing the Order status to PLACED");
                orderStatus = "PLACED";
            } catch (Exception e) {
                log.error("OrderService | placeOrder | Error occurred in payment. Changing order status to PAYMENT_FAILED", e);
                orderStatus = "PAYMENT_FAILED";
            }

            order.setOrderStatus(orderStatus);

            orderRepository.save(order);

            log.info("OrderService | placeOrder | Order placed successfully with Order Id: {}", order.getOrderId());  // Changed to getOrderId()

            return order.getOrderId();  // Changed to getOrderId()
        } else {
            throw new RuntimeException("Product with SKU code " + orderRequest.skuCode() + " is not in stock");
        }
    }

    private static Order mapToOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setProductId(orderRequest.productId());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        return order;
    }


}
