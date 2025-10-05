package model;

import java.time.LocalDate; // import the LocalDate class

/*
 * Represents a record of a single stock transaction (buy or sell).
 * Each Transaction object includes details such as the date, stock symbol,
 * action type (BUY/SELL), number of shares, price per share, and total amount.
 * This class is immutable once created and used for maintaining transaction history.
 */
public class Transaction {
    private LocalDate date;


    public Transaction() {
        // stub
    }

    // EFFECTS: display a record of a single stock transaction (buy or sell) in the console
    // with the format as 
    // | Date | Symbol | Action | Shares | Price | Total |
    public void printTransaction() {
        // stub
    }
}
