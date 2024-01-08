package com.company.bank.transaction.dao.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MILLIS;

@Data
@Document(collection = "file_parsing_request")
public class FileParsingRequest {
    private UUID id;

    @Indexed(unique = true)
    private String fileKey;
    private FileParsingRequestStatus status;
    private Instant createdAt;

    public FileParsingRequest(String fileKey) {
        this.id = UUID.randomUUID();
        this.fileKey = fileKey;
        this.status = FileParsingRequestStatus.NEW;
        this.createdAt = Instant.now().truncatedTo(MILLIS);
    }
}
