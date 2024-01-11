package com.company.bank.transaction.controller;

import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.service.FileParsingRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionReaderController.class)
class TransactionReaderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileParsingRequestService fileParsingRequestService;


    @Test
    void shouldParseFilesByPath() throws Exception {
        String fileKey = "testFileKey";
        FileParsingRequest mockParsingRequest = new FileParsingRequest(fileKey);
        when(fileParsingRequestService.createFileParsingRequest(fileKey)).thenReturn(mockParsingRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions/upload")
                        .param("fileKey", fileKey))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.fileKey").value(fileKey));

        verify(fileParsingRequestService).createFileParsingRequest(fileKey);
    }

    @Test
    void shouldGetParsingRequests() throws Exception {
        List<FileParsingRequest> mockParsingRequests = List.of(
                new FileParsingRequest("key1"),
                new FileParsingRequest("key2")
        );
        when(fileParsingRequestService.getFileParsingRequests()).thenReturn(mockParsingRequests);

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions/requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fileKey").value("key1"))
                .andExpect(jsonPath("$[1].fileKey").value("key2"));

        verify(fileParsingRequestService).getFileParsingRequests();
    }

    @Test
    void shouldGetParsingRequest() throws Exception {
        String fileKey = "key1";
        FileParsingRequest mockParsingRequest = new FileParsingRequest(fileKey);
        when(fileParsingRequestService.getFileParsingRequestByFileKey(fileKey)).thenReturn(mockParsingRequest);

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions/requests/key1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockParsingRequest.getId().toString()))
                .andExpect(jsonPath("$.fileKey").value(fileKey))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }
}
