package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPortfolio {
    // symbols string
    private static final String ORCL = "ORCL";
    private static final String AMZN = "AMZN";

    private Stock apple;
    private Stock amazon;
    private Stock google;
    private Stock nvidia;
    private Stock costco;
    private Stock jpmorgan;

    private List<Stock> testStocks;
    private double testTotalValue;
    private LocalDateTime testLastUpdated;
    private List<String> testSymbols;

    private Portfolio portfolio;

    
    @BeforeEach
    void runBefore() {
        // constructor with shares parameter
        apple = new Stock("AAPL", "Apple Inc.", "Technology", 0, 258.02);
        amazon = new Stock("AMZN", "Amazon Com Inc.", "Consumer Discretionary",0, 219.51);
        google = new Stock("GOOGL", "Alphabet Inc.", "Technology",0, 246.45);
        nvidia = new Stock("NVDA", "Nvidia Corp", "Technology", 0, 187.55);
        costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples",0, 915.95);
        jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services", 0, 310.35);

        testStocks = new ArrayList<>();
        testStocks.add(apple);
        testStocks.add(amazon);
        testStocks.add(google);
        testStocks.add(nvidia);
        testStocks.add(costco);
        testStocks.add(jpmorgan);
        testLastUpdated = LocalDateTime.now();
        portfolio = new Portfolio("Thai Binh Nguyen", 10000.00, testStocks, 14292.15, testLastUpdated);
    }

    @Test
    void testGetStock() {
        assertEquals(null, portfolio.getStock(ORCL));
        assertEquals(amazon, portfolio.getStock(AMZN));
    }

    @Test
    void testAddStockWhenStockNotExist() {
        assertFalse(testSymbols.contains(ORCL));
        Stock oracle = new Stock("ORCL", "Oracle Corp", "Technology", 0, 258.02);
        assertEquals(0 , oracle.getSharesOwned());
        assertEquals(, oracle);, testStocks.getTotalValue());
    }



    @Test
    void testConstrutorWithShares() {
        // test JPMorgan stock
        assertEquals("JPM", jpmorgan.getSymbol());
        assertEquals("JPMorgan Chase & Corp", jpmorgan.getCompanyName());
        assertEquals("Financial Services", jpmorgan.getSector());
        assertEquals(10, jpmorgan.getSharesOwned());
        assertEquals(310.35, jpmorgan.getPrice());
        assertEquals(10 * 310.35, jpmorgan.getMarketValue());
    }

    @Test
    void testConstrutorWithoutShares() {
        // test JPMorgan stock
        assertEquals("NVDA", nvidiaWithoutShares.getSymbol());
        assertEquals("Nvidia Corp", nvidiaWithoutShares.getCompanyName());
        assertEquals("Technology", nvidiaWithoutShares.getSector());
        assertEquals(0, nvidiaWithoutShares.getSharesOwned());
        assertEquals(187.55, nvidiaWithoutShares.getPrice());
        assertEquals(0 * 187.55, nvidiaWithoutShares.getMarketValue());
    }

    @Test
    void testUpdateStockPrice() {
        costco.updateStockPrice(1500.55);
        assertEquals(915.95, costco.getAveragePurchasePrice());
        assertEquals(1500.55 * 4, costco.getMarketValue());
    }

    @Test
    void testBuyShares() {
        costco.buyShares(20, 105.55);
        assertEquals(4 + 20, costco.getSharesOwned());
        assertEquals((915.95 * 4 + 105.55 * 20) / 2, costco.getAveragePurchasePrice());
    }

    @Test
    void testSellShares() {
        costco.updateStockPrice(1500.55);
        costco.sellShares(1);
        assertEquals(4 - 1, costco.getSharesOwned());
        assertEquals(1500.55 * 4 - 1500.55 * 1, costco.getMarketValue());
    }    

    @Test
    void testSellSharesWhenSellSharesMoreThanCurrentShares() {
        costco.sellShares(5);
        assertEquals(4, costco.getSharesOwned());
        assertEquals(915.95 * 4, costco.getMarketValue());
    } 
}
