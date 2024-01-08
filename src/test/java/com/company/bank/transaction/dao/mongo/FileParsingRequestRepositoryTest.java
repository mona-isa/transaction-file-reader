package com.company.bank.transaction.dao.mongo;

import com.company.bank.transaction.dao.FileParsingRequestRepository;
import com.company.bank.transaction.dao.entity.FileParsingRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class FileParsingRequestRepositoryTest {

    @Autowired
    private FileParsingRequestRepository fileParsingRequestRepository;

    @Test
    void shouldFindByFileKey_Exists() {
        String testFileKey = "testFileKey";
        FileParsingRequest fileParsingRequest = new FileParsingRequest(testFileKey);
        fileParsingRequestRepository.save(fileParsingRequest);

        Optional<FileParsingRequest> result = fileParsingRequestRepository.findByFileKey(testFileKey);

        assertTrue(result.isPresent());
        assertEquals(fileParsingRequest, result.get());
    }

    @Test
    void shouldFindByFileKey_NotExists() {
        Optional<FileParsingRequest> result = fileParsingRequestRepository.findByFileKey("nonExistentFileKey");
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetByFileKey_Exists() {
        String testFileKey = "testFileKey2";
        FileParsingRequest fileParsingRequest = new FileParsingRequest(testFileKey);
        fileParsingRequestRepository.save(fileParsingRequest);

        FileParsingRequest result = fileParsingRequestRepository.getByFileKey(testFileKey);

        assertNotNull(result);
        assertEquals(fileParsingRequest, result);
    }

    @Test
    void shouldGetByFileKey_NotExists() {
        assertThrows(RuntimeException.class, () -> fileParsingRequestRepository.getByFileKey("nonExistentFileKey"));
    }
}