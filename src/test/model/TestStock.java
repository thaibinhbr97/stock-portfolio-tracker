package model;

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
    public void runBefore() {
        // constructor with shares parameter
        apple = new Stock("AAPL", "Apple Inc.", "Technology", 258.02);
        amazon = new Stock("AMZN", "Amazon Com Inc.", "Consumer Discretionary", 219.51);
        google = new Stock("GOOGL", "Alphabet Inc.", "Technology", 246.45);
        nvidia = new Stock("NVDA", "Nvidia Corp", "Technology", 187.55);
        costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples", 915.95);
        jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services", 310.35);
    }

    @Test
    public void testConstrutor() {
        // test JPMorgan stock
        assertEquals("JPM", jpmorgan.getSymbol());
        assertEquals("JPMorgan Chase & Corp", jpmorgan.getCompanyName());
        assertEquals("Financial Services", jpmorgan.getSector());
        assertEquals(310.35, jpmorgan.getCurrentPrice());
    }

    @Test
    public void testUpdateCurrentPrice() {
        costco.updateCurrentPrice(1500.55);
        assertEquals(1500.55, costco.getCurrentPrice());
    }

    @Test
    public void testStockToString() {
        String stockString = "| NVDA | Nvidia Corp | Technology | $187.55 |";
        assertEquals(stockString, nvidia.toString());
    }
}
