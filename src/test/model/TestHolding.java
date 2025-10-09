package model;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.DoubleCompare;

public class TestHolding {
    private Stock apple;
    private Stock amazon;
    private Stock google;
    private Stock nvidia;
    private Stock costco;
    private Stock jpmorgan;

    private Holding nvidiaHolding;

    @BeforeEach
    void runBefore() {
        // constructor with shares parameter
        apple = new Stock("AAPL", "Apple Inc.", "Technology", 258.02);
        amazon = new Stock("AMZN", "Amazon Com Inc.", "Consumer Discretionary", 219.51);
        google = new Stock("GOOGL", "Alphabet Inc.", "Technology", 246.45);
        nvidia = new Stock("NVDA", "Nvidia Corp", "Technology", 150.00);
        costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples",915.95);
        jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services", 310.35);

        nvidiaHolding = new Holding(nvidia, 3.0);
    }

    @Test
    void testConstructor() {
        assertEquals(3.0 , nvidiaHolding.getShares());
        assertEquals(150.0 , nvidiaHolding.getAveragePrice());
        assertEquals(450.0 , nvidiaHolding.getMarketValue());
    }

    @Test
    void testBuyOneSamePriceWholeShare() {
        nvidiaHolding.buyShare(1.0);
        assertEquals(4.0 , nvidiaHolding.getShares());
        assertEquals(150.0 , nvidiaHolding.getAveragePrice());
        assertEquals(600.0 , nvidiaHolding.getMarketValue());        
    }

    @Test
    void testBuyManySamePriceWholeShares() {
        nvidiaHolding.buyShare(5.0);
        assertEquals(8.0 , nvidiaHolding.getShares());
        assertEquals(150.0 , nvidiaHolding.getAveragePrice());
        assertEquals(1200.0 , nvidiaHolding.getMarketValue());  
        assertEquals(0.0 , nvidiaHolding.getUnrealizedProfit());  
    }    

    @Test
    void testBuyOneSamePriceFractionalShare() {
        nvidiaHolding.buyShare(0.1);
        assertEquals(3.1 , nvidiaHolding.getShares());
        assertEquals(150.0 , nvidiaHolding.getAveragePrice());
        assertEquals(465.0 , nvidiaHolding.getMarketValue());        
    }

    @Test
    void testBuyManySamePriceFractionalShares() {
        nvidiaHolding.buyShare(5.5);
        assertEquals(8.5 , nvidiaHolding.getShares());
        assertEquals(150.0 , nvidiaHolding.getAveragePrice());
        assertEquals(1275.0 , nvidiaHolding.getMarketValue());  
        assertEquals(50.0    , nvidiaHolding.getUnrealizedProfit());  
    }

    @Test
    void testBuyOneDifferentPriceWholeShare() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(1.0);
        assertEquals(4.0 , nvidiaHolding.getShares());
        assertEquals(162.5, nvidiaHolding.getAveragePrice());
        assertEquals(650.0 , nvidiaHolding.getMarketValue());   
        assertEquals(150.0, nvidiaHolding.getUnrealizedProfit());       
    }

    @Test
    void testBuyDifferentPriceWholeShares() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(5.0);
        assertEquals(8.0 , nvidiaHolding.getShares());
        assertEquals(181.25 , nvidiaHolding.getAveragePrice());
        assertEquals(1450.0 , nvidiaHolding.getMarketValue());  
        assertEquals(150.0 , nvidiaHolding.getUnrealizedProfit());  
    }    

    @Test
    void testBuyDifferentPriceFractionalShare() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(0.1);
        assertEquals(3.1 , nvidiaHolding.getShares());
        assertTrue(DoubleCompare.compareDoubles(151.61, nvidiaHolding.getAveragePrice()) == 0);
        assertEquals(470.0 , nvidiaHolding.getMarketValue());        
    }

    @Test
    void testBuyManyDifferentPriceFractionalShares() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(5.5);
        assertEquals(8.5 , nvidiaHolding.getShares());
        assertTrue(DoubleCompare.compareDoubles(182.35, nvidiaHolding.getAveragePrice()) == 0);
        assertEquals(1550.0 , nvidiaHolding.getMarketValue());  
        assertTrue(DoubleCompare.compareDoubles(17.65, nvidiaHolding.getUnrealizedProfit()) == 0);
    }
    
    @Test
    void testSellWholeShare() {
        nvidiaHolding.sellShare(1.0);
        assertEquals(2.0 , nvidiaHolding.getShares());
        assertEquals(150.0 , nvidiaHolding.getAveragePrice());
        assertEquals(300.0 , nvidiaHolding.getMarketValue());  
        assertEquals(0.0    , nvidiaHolding.getUnrealizedProfit()); 
    }

    @Test
    void testSellFractionalShare() {
        nvidiaHolding.sellShare(2.5);
        assertEquals(0.5 , nvidiaHolding.getShares());
        assertEquals(150.0 , nvidiaHolding.getAveragePrice());
        assertEquals(75.0 , nvidiaHolding.getMarketValue());  
        assertEquals(0.0    , nvidiaHolding.getUnrealizedProfit());     
    }

    @Test
    void testSellShareLargerThanCurrentShares() {
        nvidiaHolding.sellShare(3.5);
        assertEquals(3.0 , nvidiaHolding.getShares());
        assertEquals(150.0 , nvidiaHolding.getAveragePrice());
        assertEquals(600.0 , nvidiaHolding.getMarketValue());  
        assertEquals(0.0    , nvidiaHolding.getUnrealizedProfit());     
    }    

    @Test
    void testHoldingToString() {
        String holdingString = "| NVDA | 150.0 | 150.0 | 3.0 | 450.00 | 0.0 |";
        assertTrue(holdingString.equals(nvidiaHolding.toString()));
    }

}


