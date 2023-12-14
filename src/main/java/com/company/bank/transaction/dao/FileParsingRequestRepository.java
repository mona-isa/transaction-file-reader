package com.company.bank.transaction.dao;

import com.company.bank.transaction.dao.entity.FileParsingRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileParsingRequestRepository extends MongoRepository<FileParsingRequest, String> {
    Optional<FileParsingRequest> findByFileKey(String fileKey);

    default FileParsingRequest getByFileKey(String fileKey) {
        return findByFileKey(fileKey).orElseThrow(() -> new RuntimeException("No parsing request found for " + fileKey));
    }
}
