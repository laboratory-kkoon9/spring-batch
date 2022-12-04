package com.kkoon9.springbatch.domain;

import lombok.Getter;

@Getter
public class CustomerContactUpdate extends CustomerUpdate {
    private final String emailAddress;
    private final String honePhone;
    private final String cellPhone;
    private final String workPhone;
    private final Integer notificationPreferences;

    public CustomerContactUpdate(long customerId, String emailAddress, String honePhone, String cellPhone, String workPhone, Integer notificationPreferences) {
        super(customerId);
        this.emailAddress = emailAddress;
        this.honePhone = honePhone;
        this.cellPhone = cellPhone;
        this.workPhone = workPhone;
        this.notificationPreferences = notificationPreferences;
    }
}
