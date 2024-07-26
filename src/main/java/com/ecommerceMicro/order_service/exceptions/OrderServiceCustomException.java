package com.ecommerceMicro.order_service.exceptions;

import lombok.Data;

@Data
public class OrderServiceCustomException extends RuntimeException{

    private String errorCode;
    private int status;

    public OrderServiceCustomException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}