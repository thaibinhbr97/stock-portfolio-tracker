package model;

import java.util.List;

/*
 * Represents a stock that you want to watch.
 * WatchHolding has the ability to view your current watch stock lists.
 */
public class WatchStock {
    private List<Stock> watchStock;
    private List<String> symbols;

    public WatchStock() {
        
    }

    // MODIFIES: this
    // EFFECTS: adds a stock to watch list if it is not in watchStock. Otherwise, do nothing.
    public void addStock(Stock stock) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: removes a stock to watch list if it is in watchStock. Otherwise, do nothing.
    public void removeStock(Stock stock) {
        // stub
    }

    // EFFECTS: returns the size of the watchStock
    public int size() {
        return 0;
    }

    public List<String> getSymbols() {
        return null;
    }

    // EFFECTS: Overriding toString() method of WatchStock class as below
    // | Symbol | CompanyName | Sector | CurrentPrice |    
    @Override
    public String toString() {
        return null;
    }    
}
