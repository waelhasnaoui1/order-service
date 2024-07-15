package com.ecommerceMicro.order_service.repositories;

import com.ecommerceMicro.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
