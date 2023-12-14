package com.company.bank.transaction.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String place;
    private Double amount;
    private String currency;
    private String card;

    private Client client;
}
