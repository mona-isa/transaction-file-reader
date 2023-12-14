package com.company.bank.transaction.dao.mongo;

import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.dao.entity.FileParsingRequestStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileParsingRequestTest {

    @Test
    void constructorShouldInitializeFields() {
        String fileKey = "testFileKey";
        FileParsingRequest fileParsingRequest = new FileParsingRequest(fileKey);

        assertNotNull(fileParsingRequest.getId());
        assertEquals(fileKey, fileParsingRequest.getFileKey());
        assertEquals(FileParsingRequestStatus.NEW, fileParsingRequest.getStatus());
    }
}
