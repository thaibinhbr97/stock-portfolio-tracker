package model;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a stock market containing available stocks.
 * Uses a TreeMap to keep stocks sorted alphabetically by their symbol.
 */
public class Market {
        private Map<String, Stock> market;

    // EFFECTS: constructs a new Market and loads sample stocks
    public Market() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: loads sample stocks to market
    public void loadSampleStocks() {
        // stub
    }

    // EFFECTS: returns the stock with the given symbol, or null if not found
    public Stock getStock(String symbol) {
        return null;// stub
    }

    // MODIFIES: this
    // EFFECTS: adds or replaces the given stock in the market
    public void addOrReplace(Stock stock) {
        // stub
    }

    // EFFECTS: returns a collection of all stocks in the market
    public Collection<Stock> getAllStocks() {
        return null;// stub
    }

    // EFFECTS: returns true if the market is empty
    public boolean isEmpty() {
        return false; // stub
    }
}
