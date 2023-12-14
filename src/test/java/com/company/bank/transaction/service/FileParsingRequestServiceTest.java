package com.company.bank.transaction.service;

import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.dao.FileParsingRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileParsingRequestServiceTest {

    @Mock
    private FileParsingRequestRepository fileParsingRequestRepository;

    @Mock
    private TransactionFileService transactionFileService;

    @InjectMocks
    private FileParsingRequestService underTest;


    @Test
    void testCreateFileParsingRequest() {
        String fileKey = "testFileKey";
        FileParsingRequest fileParsingRequest = new FileParsingRequest(fileKey);
        when(fileParsingRequestRepository.save(any(FileParsingRequest.class))).thenReturn(fileParsingRequest);

        FileParsingRequest result = underTest.createFileParsingRequest(fileKey);

        assertEquals(fileParsingRequest, result);
        verify(fileParsingRequestRepository).save(any(FileParsingRequest.class));
        verify(transactionFileService).parseXmlAndSaveToDatabase(fileKey);
    }

    @Test
    void testGetFileParsingRequests() {
        List<FileParsingRequest> fileParsingRequests = Arrays.asList(
                new FileParsingRequest("key1"),
                new FileParsingRequest("key2")
        );
        when(fileParsingRequestRepository.findAll()).thenReturn(fileParsingRequests);

        List<FileParsingRequest> result = underTest.getFileParsingRequests();

        assertEquals(fileParsingRequests, result);
        verify(fileParsingRequestRepository).findAll();
    }
}
