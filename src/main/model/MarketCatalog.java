package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides market listings.
 * This class only serves to seed a Market with demo data.
 */
public final class MarketCatalog {

    public MarketCatalog() {

    }

    // EFFECTS: returns a list of stocks
    public static List<Stock> marketStocks() {
        List<Stock> stocks = new ArrayList<>();
        // Technology
        stocks.add(new Stock("AAPL", "Apple Inc.", "Technology", 175.00));
        stocks.add(new Stock("MSFT", "Microsoft Corp.", "Technology", 330.00));
        stocks.add(new Stock("GOOGL", "Alphabet Inc.", "Technology", 135.00));
        stocks.add(new Stock("AMZN", "Amazon.com Inc.", "Technology", 145.00));
        stocks.add(new Stock("NVDA", "NVIDIA Corp.", "Technology", 460.00));
        stocks.add(new Stock("META", "Meta Platforms", "Technology", 310.00));
        stocks.add(new Stock("TSLA", "Tesla Inc.", "Technology", 240.00));
        stocks.add(new Stock("AVGO", "Broadcom Inc.", "Technology", 880.00));
        stocks.add(new Stock("CSCO", "Cisco Systems", "Technology", 53.00));
        stocks.add(new Stock("ADBE", "Adobe Inc.", "Technology", 550.00));
        stocks.add(new Stock("CRM", "Salesforce Inc.", "Technology", 215.00));
        stocks.add(new Stock("AMD", "Advanced Micro Devices", "Technology", 105.00));
        stocks.add(new Stock("INTC", "Intel Corp.", "Technology", 38.00));
        stocks.add(new Stock("QCOM", "Qualcomm Inc.", "Technology", 115.00));
        stocks.add(new Stock("IBM", "IBM Corp.", "Technology", 145.00));
        stocks.add(new Stock("TXN", "Texas Instruments", "Technology", 160.00));
        stocks.add(new Stock("ORCL", "Oracle Corp.", "Technology", 110.00));
        stocks.add(new Stock("NFLX", "Netflix Inc.", "Technology", 440.00));
        stocks.add(new Stock("PYPL", "PayPal Holdings", "Technology", 58.00));
        stocks.add(new Stock("UBER", "Uber Technologies", "Technology", 48.00));

        // Financials
        stocks.add(new Stock("JPM", "JPMorgan Chase", "Financials", 150.00));
        stocks.add(new Stock("BAC", "Bank of America", "Financials", 28.00));
        stocks.add(new Stock("WFC", "Wells Fargo", "Financials", 42.00));
        stocks.add(new Stock("C", "Citigroup Inc.", "Financials", 41.00));
        stocks.add(new Stock("GS", "Goldman Sachs", "Financials", 325.00));
        stocks.add(new Stock("MS", "Morgan Stanley", "Financials", 85.00));
        stocks.add(new Stock("AXP", "American Express", "Financials", 155.00));
        stocks.add(new Stock("V", "Visa Inc.", "Financials", 245.00));
        stocks.add(new Stock("MA", "Mastercard Inc.", "Financials", 410.00));
        stocks.add(new Stock("BLK", "BlackRock Inc.", "Financials", 680.00));
        stocks.add(new Stock("SCHW", "Charles Schwab", "Financials", 55.00));
        stocks.add(new Stock("USB", "US Bancorp", "Financials", 35.00));
        stocks.add(new Stock("PNC", "PNC Financial", "Financials", 120.00));
        stocks.add(new Stock("TFC", "Truist Financial", "Financials", 30.00));
        stocks.add(new Stock("COF", "Capital One", "Financials", 100.00));

        // Healthcare
        stocks.add(new Stock("UNH", "UnitedHealth Group", "Healthcare", 480.00));
        stocks.add(new Stock("JNJ", "Johnson & Johnson", "Healthcare", 160.00));
        stocks.add(new Stock("LLY", "Eli Lilly", "Healthcare", 580.00));
        stocks.add(new Stock("MRK", "Merck & Co.", "Healthcare", 105.00));
        stocks.add(new Stock("ABBV", "AbbVie Inc.", "Healthcare", 150.00));
        stocks.add(new Stock("PFE", "Pfizer Inc.", "Healthcare", 33.00));
        stocks.add(new Stock("TMO", "Thermo Fisher", "Healthcare", 510.00));
        stocks.add(new Stock("DHR", "Danaher Corp.", "Healthcare", 250.00));
        stocks.add(new Stock("ABT", "Abbott Labs", "Healthcare", 95.00));
        stocks.add(new Stock("BMY", "Bristol-Myers Squibb", "Healthcare", 58.00));
        stocks.add(new Stock("AMGN", "Amgen Inc.", "Healthcare", 265.00));
        stocks.add(new Stock("CVS", "CVS Health", "Healthcare", 70.00));
        stocks.add(new Stock("GILD", "Gilead Sciences", "Healthcare", 75.00));
        stocks.add(new Stock("ISRG", "Intuitive Surgical", "Healthcare", 295.00));
        stocks.add(new Stock("REGN", "Regeneron Pharm", "Healthcare", 820.00));

        // Consumer Discretionary
        stocks.add(new Stock("HD", "Home Depot", "Consumer Disc.", 320.00));
        stocks.add(new Stock("MCD", "McDonald's", "Consumer Disc.", 280.00));
        stocks.add(new Stock("NKE", "Nike Inc.", "Consumer Disc.", 105.00));
        stocks.add(new Stock("LOW", "Lowe's", "Consumer Disc.", 210.00));
        stocks.add(new Stock("SBUX", "Starbucks", "Consumer Disc.", 95.00));
        stocks.add(new Stock("TGT", "Target Corp.", "Consumer Disc.", 125.00));
        stocks.add(new Stock("BKNG", "Booking Holdings", "Consumer Disc.", 3100.00));
        stocks.add(new Stock("TJX", "TJX Companies", "Consumer Disc.", 90.00));
        stocks.add(new Stock("LULU", "Lululemon", "Consumer Disc.", 380.00));
        stocks.add(new Stock("F", "Ford Motor", "Consumer Disc.", 12.00));
        stocks.add(new Stock("GM", "General Motors", "Consumer Disc.", 32.00));
        stocks.add(new Stock("CMG", "Chipotle", "Consumer Disc.", 1900.00));
        stocks.add(new Stock("MAR", "Marriott Int.", "Consumer Disc.", 200.00));

        // Consumer Staples
        stocks.add(new Stock("PG", "Procter & Gamble", "Consumer Staples", 150.00));
        stocks.add(new Stock("COST", "Costco Wholesale", "Consumer Staples", 560.00));
        stocks.add(new Stock("WMT", "Walmart Inc.", "Consumer Staples", 160.00));
        stocks.add(new Stock("KO", "Coca-Cola", "Consumer Staples", 58.00));
        stocks.add(new Stock("PEP", "PepsiCo", "Consumer Staples", 170.00));
        stocks.add(new Stock("PM", "Philip Morris", "Consumer Staples", 95.00));
        stocks.add(new Stock("MO", "Altria Group", "Consumer Staples", 42.00));
        stocks.add(new Stock("CL", "Colgate-Palmolive", "Consumer Staples", 75.00));
        stocks.add(new Stock("EL", "Estee Lauder", "Consumer Staples", 145.00));
        stocks.add(new Stock("KHC", "Kraft Heinz", "Consumer Staples", 35.00));

        // Energy
        stocks.add(new Stock("XOM", "Exxon Mobil", "Energy", 115.00));
        stocks.add(new Stock("CVX", "Chevron Corp.", "Energy", 160.00));
        stocks.add(new Stock("COP", "ConocoPhillips", "Energy", 120.00));
        stocks.add(new Stock("SLB", "Schlumberger", "Energy", 58.00));
        stocks.add(new Stock("EOG", "EOG Resources", "Energy", 130.00));
        stocks.add(new Stock("MPC", "Marathon Petroleum", "Energy", 150.00));
        stocks.add(new Stock("PSX", "Phillips 66", "Energy", 120.00));
        stocks.add(new Stock("VLO", "Valero Energy", "Energy", 135.00));
        stocks.add(new Stock("OXY", "Occidental Petro", "Energy", 65.00));
        stocks.add(new Stock("KMI", "Kinder Morgan", "Energy", 17.00));

        // Industrials
        stocks.add(new Stock("CAT", "Caterpillar", "Industrials", 270.00));
        stocks.add(new Stock("UNP", "Union Pacific", "Industrials", 210.00));
        stocks.add(new Stock("UPS", "UPS", "Industrials", 155.00));
        stocks.add(new Stock("HON", "Honeywell", "Industrials", 190.00));
        stocks.add(new Stock("BA", "Boeing Co.", "Industrials", 195.00));
        stocks.add(new Stock("GE", "General Electric", "Industrials", 115.00));
        stocks.add(new Stock("LMT", "Lockheed Martin", "Industrials", 440.00));
        stocks.add(new Stock("DE", "Deere & Co.", "Industrials", 380.00));
        stocks.add(new Stock("MMM", "3M Company", "Industrials", 95.00));
        stocks.add(new Stock("RTX", "Raytheon Tech", "Industrials", 80.00));

        // Utilities
        stocks.add(new Stock("NEE", "NextEra Energy", "Utilities", 60.00));
        stocks.add(new Stock("SO", "Southern Co.", "Utilities", 68.00));
        stocks.add(new Stock("DUK", "Duke Energy", "Utilities", 90.00));
        stocks.add(new Stock("D", "Dominion Energy", "Utilities", 45.00));
        stocks.add(new Stock("AEP", "American Electric", "Utilities", 80.00));

        // Real Estate
        stocks.add(new Stock("PLD", "Prologis Inc.", "Real Estate", 110.00));
        stocks.add(new Stock("AMT", "American Tower", "Real Estate", 180.00));
        stocks.add(new Stock("EQIX", "Equinix Inc.", "Real Estate", 750.00));
        stocks.add(new Stock("CCI", "Crown Castle", "Real Estate", 100.00));
        stocks.add(new Stock("PSA", "Public Storage", "Real Estate", 260.00));

        // Materials
        stocks.add(new Stock("LIN", "Linde plc", "Materials", 380.00));
        stocks.add(new Stock("SHW", "Sherwin-Williams", "Materials", 260.00));
        stocks.add(new Stock("FCX", "Freeport-McMoRan", "Materials", 38.00));
        stocks.add(new Stock("NEM", "Newmont Corp.", "Materials", 40.00));
        stocks.add(new Stock("DOW", "Dow Inc.", "Materials", 52.00));

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
