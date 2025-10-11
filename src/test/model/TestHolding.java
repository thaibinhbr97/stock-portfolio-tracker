package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestHolding {
    private Stock apple;
    private Stock amazon;
    private Stock google;
    private Stock nvidia;
    private Stock costco;
    private Stock jpmorgan;

    private Holding nvidiaHolding;

    @BeforeEach
    public void runBefore() {
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
    public void testConstructor() {
        assertEquals(3.0, nvidiaHolding.getShares(), 1e-3);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(450.0, nvidiaHolding.getMarketValue(), 1e-3);
    }

    @Test
    public void testBuyOneSamePriceWholeShare() {
        nvidiaHolding.buyShare(1.0);
        assertEquals(4.0, nvidiaHolding.getShares(), 1e-3);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(600.0, nvidiaHolding.getMarketValue(), 1e-3);        
    }

    @Test
    public void testBuyManySamePriceWholeShares() {
        nvidiaHolding.buyShare(5.0);
        assertEquals(8.0, nvidiaHolding.getShares(), 1e-3);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(1200.0, nvidiaHolding.getMarketValue(), 1e-3);  
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), 1e-3);  
    }    

    @Test
    public void testBuyOneSamePriceFractionalShare() {
        nvidiaHolding.buyShare(0.1);
        assertEquals(3.1, nvidiaHolding.getShares(), 1e-3);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(465.0, nvidiaHolding.getMarketValue(), 1e-3);        
    }

    @Test
    public void testBuyManySamePriceFractionalShares() {
        nvidiaHolding.buyShare(5.5);
        assertEquals(8.5, nvidiaHolding.getShares(), 1e-3);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(1275.0, nvidiaHolding.getMarketValue(), 1e-3);  
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), 1e-3);; 
    }

    @Test
    public void testBuyOneDifferentPriceWholeShare() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(1.0);
        assertEquals(4.0, nvidiaHolding.getShares(), 1e-3);
        assertEquals(162.5, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(800.0, nvidiaHolding.getMarketValue(), 1e-3);   
        assertEquals(150.0, nvidiaHolding.getUnrealizedProfit(), 1e-3);       
    }

    @Test
    public void testBuyDifferentPriceWholeShares() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(5.0);
        assertEquals(8.0, nvidiaHolding.getShares(), 1e-3);
        assertEquals(181.25, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(1600.0, nvidiaHolding.getMarketValue(), 1e-3);  
        assertEquals(150, nvidiaHolding.getUnrealizedProfit(), 1e-3);  
    }    

    @Test
    public void testBuyDifferentPriceFractionalShare() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(0.1);
        assertEquals(3.1, nvidiaHolding.getShares(), 1e-3);
        assertEquals(620, nvidiaHolding.getMarketValue(), 1e-3);        
    }

    @Test
    public void testBuyManyDifferentPriceFractionalShares() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(5.5);
        assertEquals(8.5, nvidiaHolding.getShares(), 1e-3);
        assertEquals(1700., nvidiaHolding.getMarketValue(), 1e-3);  
    }
    
    @Test
    public void testSellWholeShare() {
        nvidiaHolding.sellShare(1.0);
        assertEquals(2.0, nvidiaHolding.getShares(), 1e-3);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(300.0, nvidiaHolding.getMarketValue(), 1e-3);  
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), 1e-3); 
    }

    @Test
    public void testSellFractionalShare() {
        nvidiaHolding.sellShare(2.5);
        assertEquals(0.5, nvidiaHolding.getShares(), 1e-3);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(75.0, nvidiaHolding.getMarketValue(), 1e-3);  
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), 1e-3);     
    }

    @Test
    public void testSellShareLargerThanCurrentShares() {
        nvidiaHolding.sellShare(3.5);
        assertEquals(3.0, nvidiaHolding.getShares(), 1e-3);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), 1e-3);
        assertEquals(450.0, nvidiaHolding.getMarketValue(), 1e-3);  
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), 1e-3);     
    }    

    @Test
    public void testHoldingToStringWithZeroProfit() {
        String holdingString = "| NVDA | $150.00 | $150.00 | 3.00 | $450.00 | $0.00 |";
        assertEquals(holdingString, nvidiaHolding.toString());
    }

    @Test
    public void testHoldingToStringWithProfit() {
        String holdingString = "| NVDA | $200.00 | $150.00 | 3.00 | $600.00 | +$150.00 |";
        nvidiaHolding.getStock().updateCurrentPrice(200.00);
        System.out.println(nvidiaHolding.toString());
        assertEquals(holdingString, nvidiaHolding.toString());
    }

    @Test
    public void testHoldingToStringWithLoss() {
        String holdingString = "| NVDA | $100.00 | $150.00 | 3.00 | $300.00 | -$150.00 |";
        nvidiaHolding.getStock().updateCurrentPrice(100.00);
        System.out.println(nvidiaHolding.toString());
        assertEquals(holdingString, nvidiaHolding.toString());
    }

}


