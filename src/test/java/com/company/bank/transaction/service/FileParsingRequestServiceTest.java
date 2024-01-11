package com.company.bank.transaction.service;

import com.company.bank.transaction.dao.FileParsingRequestRepository;
import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.exception.IllegalRequestParamException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.Arrays;
import java.util.List;

import static com.company.bank.transaction.exception.entity.ErrorType.WARNING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void shouldCreateFileParsingRequest() {
        String fileKey = "testFileKey";
        FileParsingRequest fileParsingRequest = new FileParsingRequest(fileKey);
        when(fileParsingRequestRepository.save(any(FileParsingRequest.class))).thenReturn(fileParsingRequest);

        FileParsingRequest result = underTest.createFileParsingRequest(fileKey);

        assertEquals(fileParsingRequest, result);
        verify(fileParsingRequestRepository).save(any(FileParsingRequest.class));
        verify(transactionFileService).parseXmlAndSaveToDatabase(fileKey);
    }

    @Test
    void shouldThrowIllegalRequestParamExceptionWhenDuplicateKey() {
        String fileKey = "testFileKey";
        FileParsingRequest existingRequest = new FileParsingRequest(fileKey);
        when(fileParsingRequestRepository.getByFileKey(fileKey)).thenReturn(existingRequest);

        when(fileParsingRequestRepository.save(any(FileParsingRequest.class)))
                .thenThrow(new DuplicateKeyException("Duplicate key error index"));

        IllegalRequestParamException exception = assertThrows(IllegalRequestParamException.class,
                () -> underTest.createFileParsingRequest(fileKey));

        assertEquals("fileKey", exception.getField());
        assertEquals("Request for the file key already exists and won't be recreated." + existingRequest,
                exception.getMessage());
        assertEquals(WARNING, exception.getErrorType());
    }

    @Test
    void shouldGetFileParsingRequests() {
        List<FileParsingRequest> fileParsingRequests = Arrays.asList(
                new FileParsingRequest("key1"),
                new FileParsingRequest("key2")
        );
        when(fileParsingRequestRepository.findAll()).thenReturn(fileParsingRequests);

        List<FileParsingRequest> result = underTest.getFileParsingRequests();

        assertEquals(fileParsingRequests, result);
        verify(fileParsingRequestRepository).findAll();
    }

    @Test
    void shouldGetFileParsingRequestBeFileKey() {
        String fileKey = "key";
        FileParsingRequest fileParsingRequest = new FileParsingRequest(fileKey);
        when(fileParsingRequestRepository.getByFileKey(fileKey)).thenReturn(fileParsingRequest);

        FileParsingRequest result = underTest.getFileParsingRequestByFileKey(fileKey);

        assertEquals(fileParsingRequest, result);
    }
}
