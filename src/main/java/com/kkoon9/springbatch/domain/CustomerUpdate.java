package com.kkoon9.springbatch.domain;

import lombok.Getter;

@Getter
public class CustomerUpdate {
    protected final long customerId;

    public CustomerUpdate(long customerId) {
        this.customerId = customerId;
    }
}
