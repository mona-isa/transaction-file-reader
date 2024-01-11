package com.company.bank.transaction.dao.mongo;

import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.dao.entity.FileParsingRequestStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileParsingRequestTest {

    @Test
    void shouldCreateFileParsingRequest() {
        String fileKey = "testFileKey";
        FileParsingRequest result = new FileParsingRequest(fileKey);

        assertNotNull(result.getId());
        assertEquals(fileKey, result.getFileKey());
        assertEquals(FileParsingRequestStatus.NEW, result.getStatus());
        assertThat(result.getCreatedAt()).isBefore(Instant.now());
    }
}
