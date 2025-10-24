package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides market listings.
 * This class only serves to seed a Market with demo data.
 */
public final class MarketCatalog {

    private MarketCatalog() { } // prevent instantiation

    // EFFECTS: returns a list of stocks
    public static List<Stock> marketStocks() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("AAPL", "Apple Inc.", "Technology", 150.00));
        stocks.add(new Stock("AMZN", "Amazon.com, Inc.", "Consumer Discretionary", 3300.00));
        stocks.add(new Stock("GOOGL", "Alphabet Inc.", "Technology", 2800.00));
        stocks.add(new Stock("MSFT", "Microsoft Corporation", "Technology", 350.00));
        stocks.add(new Stock("TSLA", "Tesla, Inc.", "Automotive", 250.00));
        stocks.add(new Stock("NFLX", "Netflix, Inc.", "Communication Services", 600.00));
        stocks.add(new Stock("NVDA", "NVIDIA Corporation", "Technology", 900.00));
        stocks.add(new Stock("META", "Meta Platforms, Inc.", "Technology", 470.00));
        stocks.add(new Stock("BABA", "Alibaba Group Holding Limited", "Consumer Discretionary", 85.00));
        stocks.add(new Stock("ORCL", "Oracle Corporation", "Technology", 120.00));        
        return stocks;
    }

    // MODIFIES: market
    // EFFECTS: loads the sample stocks into the given market
    public static void seed(Market market) {
        for (Stock s : marketStocks()) {
            market.addOrReplace(s);
        }
    }
}
