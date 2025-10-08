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

    private Holding testHolding;

    @BeforeEach
        void runBefore() {
            // constructor with shares parameter
            apple = new Stock("AAPL", "Apple Inc.", "Technology", 258.02);
            amazon = new Stock("AMZN", "Amazon Com Inc.", "Consumer Discretionary", 219.51);
            google = new Stock("GOOGL", "Alphabet Inc.", "Technology", 246.45);
            nvidia = new Stock("NVDA", "Nvidia Corp", "Technology", 150.00);
            costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples",915.95);
            jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services", 310.35);

            testHolding = new Holding(nvidia, 3.0);
    }

    @Test
    void testConstructor() {
        assertEquals(3.0 , testHolding.getShares());
        assertEquals(150.0 , testHolding.getAveragePrice());
        assertEquals(450.0 , testHolding.getMarketValue());
    }

    @Test
    void testBuyOneSamePriceWholeShare() {
        testHolding.buyShare(1.0);
        assertEquals(4.0 , testHolding.getShares());
        assertEquals(150.0 , testHolding.getAveragePrice());
        assertEquals(600.0 , testHolding.getMarketValue());        
    }

    @Test
    void testBuyManySamePriceWholeShares() {
        testHolding.buyShare(5.0);
        assertEquals(8.0 , testHolding.getShares());
        assertEquals(150.0 , testHolding.getAveragePrice());
        assertEquals(1200.0 , testHolding.getMarketValue());  
        assertEquals(0.0 , testHolding.getUnrealizedProfit());  
    }    

    @Test
    void testBuyOneSamePriceFractionalShare() {
        testHolding.buyShare(0.1);
        assertEquals(3.1 , testHolding.getShares());
        assertEquals(150.0 , testHolding.getAveragePrice());
        assertEquals(465.0 , testHolding.getMarketValue());        
    }

    @Test
    void testBuyManySamePriceFractionalShares() {
        testHolding.buyShare(5.5);
        assertEquals(8.5 , testHolding.getShares());
        assertEquals(150.0 , testHolding.getAveragePrice());
        assertEquals(1275.0 , testHolding.getMarketValue());  
        assertEquals(50.0    , testHolding.getUnrealizedProfit());  
    }

    @Test
    void testBuyOneDifferentPriceWholeShare() {
        testHolding.getStock().updateCurrentPrice(200.0);
        testHolding.buyShare(1.0);
        assertEquals(4.0 , testHolding.getShares());
        assertEquals(162.5, testHolding.getAveragePrice());
        assertEquals(650.0 , testHolding.getMarketValue());   
        assertEquals(150.0, testHolding.getUnrealizedProfit());       
    }

    @Test
    void testBuyDifferentPriceWholeShares() {
        testHolding.getStock().updateCurrentPrice(200.0);
        testHolding.buyShare(5.0);
        assertEquals(8.0 , testHolding.getShares());
        assertEquals(181.25 , testHolding.getAveragePrice());
        assertEquals(1450.0 , testHolding.getMarketValue());  
        assertEquals(150.0 , testHolding.getUnrealizedProfit());  
    }    

    @Test
    void testBuyDifferentPriceFractionalShare() {
        testHolding.getStock().updateCurrentPrice(200.0);
        testHolding.buyShare(0.1);
        assertEquals(3.1 , testHolding.getShares());
        assertTrue(DoubleCompare.compareDoubles(151.61, testHolding.getAveragePrice()) == 0);
        assertEquals(470.0 , testHolding.getMarketValue());        
    }

    @Test
    void testBuyManyDifferentPriceFractionalShares() {
        testHolding.getStock().updateCurrentPrice(200.0);
        testHolding.buyShare(5.5);
        assertEquals(8.5 , testHolding.getShares());
        assertTrue(DoubleCompare.compareDoubles(182.35, testHolding.getAveragePrice()) == 0);
        assertEquals(1550.0 , testHolding.getMarketValue());  
        assertTrue(DoubleCompare.compareDoubles(17.65, testHolding.getUnrealizedProfit()) == 0);
    }
    
    @Test
    void testSellWholeShare() {
        testHolding.sellShare(1.0);
        assertEquals(2.0 , testHolding.getShares());
        assertEquals(150.0 , testHolding.getAveragePrice());
        assertEquals(300.0 , testHolding.getMarketValue());  
        assertEquals(0.0    , testHolding.getUnrealizedProfit()); 
    }

    @Test
    void testSellFractionalShare() {
        testHolding.sellShare(2.5);
        assertEquals(0.5 , testHolding.getShares());
        assertEquals(150.0 , testHolding.getAveragePrice());
        assertEquals(75.0 , testHolding.getMarketValue());  
        assertEquals(0.0    , testHolding.getUnrealizedProfit());     
    }

    @Test
    void testSellShareLargerThanCurrentShares() {
        testHolding.sellShare(3.5);
        assertEquals(3.0 , testHolding.getShares());
        assertEquals(150.0 , testHolding.getAveragePrice());
        assertEquals(600.0 , testHolding.getMarketValue());  
        assertEquals(0.0    , testHolding.getUnrealizedProfit());     
    }    
}


