package com.company.bank.transaction.service;

import com.company.bank.transaction.dao.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlParserServiceTest {

    private final XmlParserService underTest = new XmlParserService();

    @Test
    void shouldGetTransactionsFromXmlFile() {
        InputStream inputStream = getClass().getResourceAsStream("/test_transactions.xml");

        List<Transaction> actualTransactions = underTest.getTransactionsFromXmlFile(inputStream);

        assertEquals(12, actualTransactions.size());

        assertTransactionPresent(actualTransactions, "A PLACE 1", 10.01, "UAH", "123456****1234", "Ivan", "Mazepa", "Mazepa", "1234567890");
        assertTransactionPresent(actualTransactions, "A PLACE 4", 12.01, "EUR", "123456****1234", "Ivan", "Gonta", "Gonta", "1234567891");
    }

    private void assertTransactionPresent(List<Transaction> transactions, String place, double amount, String currency,
                                          String card, String firstName, String lastName, String middleName, String inn) {
        assertTrue(transactions.stream()
                .anyMatch(t -> t.getPlace().equals(place)
                        && t.getAmount() == amount
                        && t.getCurrency().equals(currency)
                        && t.getCard().equals(card)
                        && t.getClient().getFirstName().equals(firstName)
                        && t.getClient().getLastName().equals(lastName)
                        && t.getClient().getMiddleName().equals(middleName)
                        && t.getClient().getInn().equals(inn)));
    }
}
