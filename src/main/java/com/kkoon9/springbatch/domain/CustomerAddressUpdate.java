package com.kkoon9.springbatch.domain;

import lombok.Getter;

@Getter
public class CustomerAddressUpdate extends CustomerUpdate {
    private final String address1;
    private final String address2;
    private final String city;
    private final String state;
    private final String postalCode;

    public CustomerAddressUpdate(long customerId, String address1, String address2, String city, String state, String postalCode) {
        super(customerId);
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }
}
