package com.company.bank.transaction.service;

import com.company.bank.transaction.dao.FileParsingRequestRepository;
import com.company.bank.transaction.dao.TransactionRepository;
import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.dao.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.List;

import static com.company.bank.transaction.dao.entity.FileParsingRequestStatus.ERROR;
import static com.company.bank.transaction.dao.entity.FileParsingRequestStatus.PROCESSED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionFileServiceTest {

    private static final String FILE_KEY = "testFileKey";

    @Mock
    private XmlParserService xmlParserService;
    @Mock
    private S3Service s3Service;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private FileParsingRequestRepository fileParsingRequestRepository;

    @InjectMocks
    private TransactionFileService underTest;


    @Test
    void shouldParseXmlAndSaveToDatabase_Success() {
        InputStream inputStreamMock = mock(InputStream.class);
        when(s3Service.getObjectInputStream(FILE_KEY)).thenReturn(inputStreamMock);
        when(xmlParserService.getTransactionsFromXmlFile(inputStreamMock)).thenReturn(List.of(new Transaction()));

        FileParsingRequest parsingRequest = new FileParsingRequest(FILE_KEY);
        when(fileParsingRequestRepository.getByFileKey(FILE_KEY)).thenReturn(parsingRequest);

        underTest.parseXmlAndSaveToDatabase(FILE_KEY);

        verify(transactionRepository).saveAll(anyList());
        verify(fileParsingRequestRepository).save(parsingRequest);
        assertThat(parsingRequest.getStatus()).isEqualTo(PROCESSED);
    }

    @Test
    void shouldParseXmlAndSaveToDatabase_Error() {
        InputStream inputStreamMock = mock(InputStream.class);
        when(s3Service.getObjectInputStream(FILE_KEY)).thenReturn(inputStreamMock);
        when(xmlParserService.getTransactionsFromXmlFile(inputStreamMock)).thenThrow(new RuntimeException("Parsing error"));

        FileParsingRequest parsingRequest = new FileParsingRequest(FILE_KEY);
        when(fileParsingRequestRepository.getByFileKey(FILE_KEY)).thenReturn(parsingRequest);

        underTest.parseXmlAndSaveToDatabase(FILE_KEY);

        verifyNoInteractions(transactionRepository);
        verify(fileParsingRequestRepository).save(parsingRequest);
        assertThat(parsingRequest.getStatus()).isEqualTo(ERROR);
    }
}
