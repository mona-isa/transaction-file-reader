package com.company.bank.transaction.service;

import com.company.bank.transaction.dao.FileParsingRequestRepository;
import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.exception.IllegalRequestParamException;
import com.company.bank.transaction.exception.entity.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileParsingRequestService {

    public static final String EXISTING_FILE_KEY = "Request for the file key already exists and won't be recreated.";

    private final FileParsingRequestRepository fileParsingRequestRepository;
    private final TransactionFileService transactionFileService;

    public FileParsingRequest createFileParsingRequest(String fileKey) {
        FileParsingRequest fileParsingRequest = new FileParsingRequest(fileKey);
        try {
            FileParsingRequest newParsingRequest = fileParsingRequestRepository.save(fileParsingRequest);
            transactionFileService.parseXmlAndSaveToDatabase(fileKey);
            return newParsingRequest;

        } catch (DuplicateKeyException e) {
            FileParsingRequest request = fileParsingRequestRepository.getByFileKey(fileKey);
            throw new IllegalRequestParamException("fileKey", EXISTING_FILE_KEY + request, ErrorType.WARNING);
        }
    }

    public List<FileParsingRequest> getFileParsingRequests() {
        return fileParsingRequestRepository.findAll();
    }

    public FileParsingRequest getFileParsingRequestByFileKey(String fileKey) {
        return fileParsingRequestRepository.getByFileKey(fileKey);
    }
}
