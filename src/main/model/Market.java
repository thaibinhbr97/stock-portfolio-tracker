package model;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a stock market containing available stocks.
 * Internally uses a TreeMap to keep stocks sorted alphabetically by their symbol.
 * This class is the single source of truth for available listings that the UI and Portfolio consult.
 */
public class Market {
    private final Map<String, Stock> market;

    // EFFECTS: constructs an empty market
    public Market() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds or replaces the given stock in the market
    public void addOrReplace(Stock stock) {
        // if (stock == null || stock.getSymbol() == null) {
        //     return;
        // }
        // market.put(stock.getSymbol().toUpperCase(), stock);
    }

    // MODIFIES: this
    // EFFECTS: adds all given stocks (nulls ignored)
    public void addAll(Collection<Stock> stocks) {
        // stub
    }

    // EFFECTS: returns the stock with the given symbol, or null if not found
    public Stock getStock(String symbol) {
        return null; // stub
    }


    // EFFECTS: returns an unmodifiable view of all stocks in the market (sorted by symbol)
    public Collection<Stock> getAllStocks() {
        return null;// stub
    }

    // EFFECTS: returns true if the market is empty
    public boolean isEmpty() {
        return false; // stub
    }
}
