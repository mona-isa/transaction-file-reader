package com.company.bank.transaction.controller;

import com.company.bank.transaction.dao.entity.FileParsingRequest;
import com.company.bank.transaction.service.FileParsingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionReaderController {

    private final FileParsingRequestService fileParsingRequestService;

    @Autowired
    public TransactionReaderController(FileParsingRequestService fileParsingRequestService) {
        this.fileParsingRequestService = fileParsingRequestService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public FileParsingRequest parseFilesByPath(@RequestParam("fileKey") String fileKey) {
        return fileParsingRequestService.createFileParsingRequest(fileKey);
    }

    @GetMapping("/requests")
    public List<FileParsingRequest> getParsingRequests() {
        return fileParsingRequestService.getFileParsingRequests();
    }

}
