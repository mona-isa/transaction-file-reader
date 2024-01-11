package com.company.bank.transaction.service;

import com.company.bank.transaction.dao.FileParsingRequestRepository;
import com.company.bank.transaction.dao.TransactionRepository;
import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.dao.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

import static com.company.bank.transaction.dao.entity.FileParsingRequestStatus.ERROR;
import static com.company.bank.transaction.dao.entity.FileParsingRequestStatus.PROCESSED;

@Service
@RequiredArgsConstructor
public class TransactionFileService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionFileService.class);

    private final XmlParserService xmlParserService;
    private final S3Service s3Service;
    private final TransactionRepository transactionRepository;
    private final FileParsingRequestRepository fileParsingRequestRepository;


    @Async("taskExecutor")
    public void parseXmlAndSaveToDatabase(String fileKey) {
        FileParsingRequest parsingRequest = fileParsingRequestRepository.getByFileKey(fileKey);
        try {
            InputStream objectInputStream = s3Service.getObjectInputStream(fileKey);
            List<Transaction> transactions = xmlParserService.getTransactionsFromXmlFile(objectInputStream);

            transactionRepository.saveAll(transactions);
            parsingRequest.setStatus(PROCESSED);
            logger.info("File {} with {} transactions has been parsed and persisted", parsingRequest, transactions.size());
        } catch (Exception e) {
            parsingRequest.setStatus(ERROR);
            logger.error("Error during parsing file {}", fileKey, e);
        }
        fileParsingRequestRepository.save(parsingRequest);
    }
}
