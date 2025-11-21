package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestHolding {
    public static final double EPSILON = 1e-9;

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
        costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples", 915.95);
        jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services", 310.35);

        nvidiaHolding = new Holding(nvidia, 3.0, 150.00);
    }

    @Test
    public void testConstructor() {
        assertEquals(3.0, nvidiaHolding.getShares(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(450.0, nvidiaHolding.getMarketValue(), EPSILON);
    }

    @Test
    public void testBuyOneSamePriceWholeShare() {
        nvidiaHolding.buyShare(1.0);
        assertEquals(4.0, nvidiaHolding.getShares(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(600.0, nvidiaHolding.getMarketValue(), EPSILON);
    }

    @Test
    public void testBuyManySamePriceWholeShares() {
        nvidiaHolding.buyShare(5.0);
        assertEquals(8.0, nvidiaHolding.getShares(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(1200.0, nvidiaHolding.getMarketValue(), EPSILON);
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), EPSILON);
    }

    @Test
    public void testBuyOneSamePriceFractionalShare() {
        nvidiaHolding.buyShare(0.1);
        assertEquals(3.1, nvidiaHolding.getShares(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(465.0, nvidiaHolding.getMarketValue(), EPSILON);
    }

    @Test
    public void testBuyManySamePriceFractionalShares() {
        nvidiaHolding.buyShare(5.5);
        assertEquals(8.5, nvidiaHolding.getShares(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(1275.0, nvidiaHolding.getMarketValue(), EPSILON);
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), EPSILON);
        ;
    }

    @Test
    public void testBuyOneDifferentPriceWholeShare() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(1.0);
        assertEquals(4.0, nvidiaHolding.getShares(), EPSILON);
        assertEquals(162.5, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(800.0, nvidiaHolding.getMarketValue(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getUnrealizedProfit(), EPSILON);
    }

    @Test
    public void testBuyDifferentPriceWholeShares() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(5.0);
        assertEquals(8.0, nvidiaHolding.getShares(), EPSILON);
        assertEquals(181.25, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(1600.0, nvidiaHolding.getMarketValue(), EPSILON);
        assertEquals(150, nvidiaHolding.getUnrealizedProfit(), EPSILON);
    }

    @Test
    public void testBuyDifferentPriceFractionalShare() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(0.1);
        assertEquals(3.1, nvidiaHolding.getShares(), EPSILON);
        assertEquals(620, nvidiaHolding.getMarketValue(), EPSILON);
    }

    @Test
    public void testBuyManyDifferentPriceFractionalShares() {
        nvidiaHolding.getStock().updateCurrentPrice(200.0);
        nvidiaHolding.buyShare(5.5);
        assertEquals(8.5, nvidiaHolding.getShares(), EPSILON);
        assertEquals(1700., nvidiaHolding.getMarketValue(), EPSILON);
    }

    @Test
    public void testSellWholeShare() {
        nvidiaHolding.sellShare(1.0);
        assertEquals(2.0, nvidiaHolding.getShares(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(300.0, nvidiaHolding.getMarketValue(), EPSILON);
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), EPSILON);
    }

    @Test
    public void testSellFractionalShare() {
        nvidiaHolding.sellShare(2.5);
        assertEquals(0.5, nvidiaHolding.getShares(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(75.0, nvidiaHolding.getMarketValue(), EPSILON);
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), EPSILON);
    }

    @Test
    public void testSellShareLargerThanCurrentShares() {
        nvidiaHolding.sellShare(3.5);
        assertEquals(3.0, nvidiaHolding.getShares(), EPSILON);
        assertEquals(150.0, nvidiaHolding.getAveragePrice(), EPSILON);
        assertEquals(450.0, nvidiaHolding.getMarketValue(), EPSILON);
        assertEquals(0.0, nvidiaHolding.getUnrealizedProfit(), EPSILON);
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

    @Test
    public void testDetachedHoldingBuyUsesAveragePriceWhenNoStockAttached() {
        // Detached holding: no Stock bound yet
        Holding h = new Holding("NVDA", 2.0, 100.0);
        // buy 1 share; since stock == null, average should be recomputed using current
        // avg (100)
        h.buyShare(1.0);
        assertEquals(3.0, h.getShares(), EPSILON);
        assertEquals(100.0, h.getAveragePrice(), EPSILON);
        // Market value is 0 until we attach a Stock (price unknown)
        assertEquals(0.0, h.getMarketValue(), EPSILON);

        // Attach market stock and ensure valuation reflects live price
        Stock nvdaLive = new Stock("NVDA", "NVIDIA", "Technology", 120.0);
        h.setStock(nvdaLive);
        assertEquals(360.0, h.getMarketValue(), EPSILON); // 3 * 120
        assertEquals(60.0, h.getUnrealizedProfit(), EPSILON); // (120-100) * 3
    }

    @Test
    public void testSetStockUpdatesSymbolCanonicalCase() {
        Holding h = new Holding("nvda", 1.0, 50.0);
        h.setStock(new Stock("NVDA", "NVIDIA", "Technology", 150.0));
        assertEquals("NVDA", h.getSymbol());
    }

    @Test
    public void testToJsonFromJsonDetachedRoundTrip() {
        Holding h = new Holding("NVDA", 3.0, 150.0);
        JSONObject o = h.toJson();
        assertEquals("NVDA", o.getString("symbol"));
        assertEquals(3.0, o.getDouble("shares"), EPSILON);
        assertEquals(150.0, o.getDouble("averagePurchasePrice"), EPSILON);
        // Should not embed a nested stock object
        assertFalse(o.has("stock"));

        // Rebuild
        Holding rebuilt = Holding.fromJson(o);
        assertEquals("NVDA", rebuilt.getSymbol());
        assertEquals(3.0, rebuilt.getShares(), EPSILON);
        assertEquals(150.0, rebuilt.getAveragePrice(), EPSILON);
        // Still detached until we attach a Stock
        assertEquals(0.0, rebuilt.getMarketValue(), EPSILON);
    }

    @Test
    public void testToStringWhenNoStockAttachedIsNullSafe() {
        Holding h = new Holding("NVDA", 3.0, 150.0);
        // With no stock, current price = 0; market value = 0; profit = (0 - 150) * 3 =
        // -450
        String expected = "| NVDA | $0.00 | $150.00 | 3.00 | $0.00 | -$450.00 |";
        assertEquals(expected, h.toString());
    }

    @Test
    public void testUpdateAveragePriceMutator() {
        nvidiaHolding.updateAveragePrice(175.0);
        assertEquals(175.0, nvidiaHolding.getAveragePrice(), EPSILON);
    }

}
