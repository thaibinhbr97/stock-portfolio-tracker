package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStock {
    private Stock apple;
    private Stock amazon;
    private Stock google;
    private Stock nvidia;
    private Stock costco;
    private Stock jpmorgan;
    
    @BeforeEach
    void runBefore() {
        // constructor with shares parameter
        apple = new Stock("AAPL", "Apple Inc.", "Technology", 258.02);
        amazon = new Stock("AMZN", "Amazon Com Inc.", "Consumer Discretionary", 219.51);
        google = new Stock("GOOGL", "Alphabet Inc.", "Technology", 246.45);
        nvidia = new Stock("NVDA", "Nvidia Corp", "Technology", 187.55);
        costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples",915.95);
        jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services", 310.35);
    }

    @Test
    void testConstrutor() {
        // test JPMorgan stock
        assertEquals("JPM", jpmorgan.getSymbol());
        assertEquals("JPMorgan Chase & Corp", jpmorgan.getCompanyName());
        assertEquals("Financial Services", jpmorgan.getSector());
        assertEquals(310.35, jpmorgan.getCurrentPrice());

        // assertEquals(10, jpmorgan.getSharesOwned());
        // assertEquals(10 * 310.35, jpmorgan.getMarketValue());
    }

    // @Test
    // void testConstrutorWithoutShares() {
    //     // test NVDA stock
    //     assertEquals("NVDA", nvidiaWithoutShares.getSymbol());
    //     assertEquals("Nvidia Corp", nvidiaWithoutShares.getCompanyName());
    //     assertEquals("Technology", nvidiaWithoutShares.getSector());
    //     assertEquals(0, nvidiaWithoutShares.getSharesOwned());
    //     assertEquals(187.55, nvidiaWithoutShares.getStockPrice());
    //     assertEquals(0 * 187.55, nvidiaWithoutShares.getMarketValue());
    // }

    @Test
    void testUpdateCurrentPrice() {
        costco.updateCurrentPrice(1500.55);
        assertEquals(1500.55, costco.getCurrentPrice());

        // assertEquals(1500.55 * 4, costco.getMarketValue());
    }

    @Test
    void testPrintStock() {
        String stockString = "| NVDA | Nvidia Corp | Technology | 187.55 |";
        assertTrue(stockString.equals(nvidia.stockToString()));
    }

    // @Test
    // void testBuyShares() {
    //     costco.buyShares(20, 105.55);
    //     assertEquals(4 + 20, costco.getSharesOwned());
    // }

    // @Test
    // void testSellShares() {
    //     costco.updateStockPrice(1500.55);
    //     costco.sellShares(1);
    //     assertEquals(4 - 1, costco.getSharesOwned());
    //     assertEquals(1500.55 * 4 - 1500.55 * 1, costco.getMarketValue());
    // }    

    // @Test
    // void testSellSharesWhenSellSharesMoreThanCurrentShares() {
    //     costco.sellShares(5);
    //     assertEquals(4, costco.getSharesOwned());
    //     assertEquals(915.95 * 4, costco.getMarketValue());
    // } 
}
