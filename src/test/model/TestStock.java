package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStock {
    Stock apple;
    Stock amazon;
    Stock google;
    Stock nvidia;
    Stock nvidiaWithoutShares;
    Stock costco;
    Stock jpmorgan;
    
    @BeforeEach
    void runBefore() {
        // constructor with shares parameter
        apple = new Stock("AAPL", "Apple Inc.", "Technology", 10, 258.02);
        amazon = new Stock("AMZN", "Amazon Com Inc.", "Consumer Discretionary", 5, 219.51);
        google = new Stock("GOOGL", "Alphabet Inc.", "Technology", 8, 246.45);
        nvidia = new Stock("NVDA", "Nvidia Corp", "Technology", 10, 187.55);
        costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples", 4, 915.95);
        jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services", 10, 310.35);

        // constructor without shares parameter
        nvidiaWithoutShares = new Stock("NVDA", "Nvidia Corp", "Technology", 187.55);
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
