package com.company.bank.transaction.service;

import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.dao.FileParsingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileParsingRequestService {

    private final FileParsingRequestRepository fileParsingRequestRepository;
    private final TransactionFileService transactionFileService;

    public FileParsingRequest createFileParsingRequest(String fileKey) {
        FileParsingRequest fileParsingRequest = new FileParsingRequest(fileKey);
        FileParsingRequest newParsingRequest = fileParsingRequestRepository.save(fileParsingRequest);

        transactionFileService.parseXmlAndSaveToDatabase(fileKey);

        return newParsingRequest;
    }

    public List<FileParsingRequest> getFileParsingRequests() {
        return fileParsingRequestRepository.findAll();
    }
}
