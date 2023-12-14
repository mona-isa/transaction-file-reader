package com.company.bank.transaction.dao;

import com.company.bank.transaction.dao.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
