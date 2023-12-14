package com.company.bank.transaction.service;

import com.company.bank.transaction.dao.entity.Client;
import com.company.bank.transaction.dao.entity.Transaction;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlParserService {

    public List<Transaction> getTransactionsFromXmlFile(InputStream inputStream) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SaxHandler handler = new SaxHandler();

            saxParser.parse(inputStream, handler);
            return handler.getTransactions();
        } catch (Exception e) {
            throw new RuntimeException("Error during file parsing", e);
        }
    }

    static class SaxHandler extends DefaultHandler {

        @Getter
        private final List<Transaction> transactions = new ArrayList<>();
        private Transaction currentTransaction;
        private StringBuilder currentData;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            currentData = new StringBuilder();

            if ("transaction".equals(qName)) {
                currentTransaction = new Transaction();
                currentTransaction.setClient(new Client());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            currentData.append(new String(ch, start, length).trim());
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            switch (qName) {
                case "transaction" -> transactions.add(currentTransaction);
                case "place" -> currentTransaction.setPlace(currentData.toString());
                case "amount" -> currentTransaction.setAmount(Double.parseDouble(currentData.toString()));
                case "currency" -> currentTransaction.setCurrency(currentData.toString());
                case "card" -> currentTransaction.setCard(currentData.toString());
                case "firstName" -> currentTransaction.getClient().setFirstName(currentData.toString());
                case "lastName" -> currentTransaction.getClient().setLastName(currentData.toString());
                case "middleName" -> currentTransaction.getClient().setMiddleName(currentData.toString());
                case "inn" -> currentTransaction.getClient().setInn(currentData.toString());
            }
        }

    }
}
