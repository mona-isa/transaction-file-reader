package com.company.bank.transaction.dao.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "file_parsing_request")
public final class FileParsingRequest {
    private UUID id;
    private String fileKey;
    private FileParsingRequestStatus status;

    public FileParsingRequest(String fileKey) {
        this.id = UUID.randomUUID();
        this.fileKey = fileKey;
        this.status = FileParsingRequestStatus.NEW;
    }
}
