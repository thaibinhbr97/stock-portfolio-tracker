package model;

import java.util.List;

/*
 * Represents a stock that you want to watch.
 * WatchHolding has the ability to view your current watch stock lists.
 */
public class WatchStock {
    private List<Stock> watchStock;

    public WatchStock() {
        
    }

    // MODIFIES: this
    // EFFECTS: adds a stock to watch lists if it is in watchStocks. Otherwise, does nothing.
    public void addStock() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: removes a stock to watch lists if it is in watchStocks. Otherwise, does nothing.
    public void removeStock() {
        // stub
    }

    // EFFECTS: prints out WatchStock with the format below
    // | Symbol | CompanyName | Sector | CurrentPrice |
    public void printWatchStock() {
        
    }
}
