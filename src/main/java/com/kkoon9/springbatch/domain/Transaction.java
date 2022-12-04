package com.kkoon9.springbatch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement(name = "transaction")
@NoArgsConstructor
@Getter
public class Transaction {
    private long transactionId;
    private long accountId;
    private String description;
    private BigDecimal credit;
    private BigDecimal debit;
    private Date timestamp;

    public Transaction(long transactionId, long accountId, String description, BigDecimal credit, BigDecimal debit, Date timestamp) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.description = description;
        this.credit = credit;
        this.debit = debit;
        this.timestamp = timestamp;
    }

    @XmlJavaTypeAdapter(JaxbDateSerializer.class)
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getTransactionAmount() {
        if (this.credit != null) {
            if (this.debit != null) {
                return this.credit.add(debit);
            } else {
                return this.credit;
            }
        } else if (this.debit != null) {
            return this.debit;
        } else {
            return new BigDecimal("0");
        }
    }
}
