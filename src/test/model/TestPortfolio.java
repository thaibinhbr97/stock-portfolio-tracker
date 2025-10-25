package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPortfolio {
    // symbol strings
    private static final String ORCL = "ORCL";
    private static final String AAPL = "AAPL";
    private static final String AMZN = "AMZN";
    private static final String GOOGL = "GOOGL";
    private static final String NVDA = "NVDA";
    private static final String COST = "COST";
    private static final String JPM = "JPM";
    
    private Stock apple;
    private Stock amazon;
    private Stock google;
    private Stock nvidia;
    private Stock costco;
    private Stock jpmorgan;
    private Stock nullStock;

    private Portfolio portfolio;
    
    @BeforeEach
    public void runBefore() {
        // constructor with shares parameter
        apple = new Stock("AAPL", "Apple Inc.", "Technology",250.00);
        amazon = new Stock("AMZN", "Amazon Com Inc.", "Consumer Discretionary",200.00);
        google = new Stock("GOOGL", "Alphabet Inc.", "Technology",150.00);
        nvidia = new Stock("NVDA", "Nvidia Corp", "Technology",150.00);
        costco = new Stock("COST", "Costco Wholesale Corp.", "Consumer Staples",450.00);
        jpmorgan = new Stock("JPM", "JPMorgan Chase & Corp", "Financial Services",300.00);

        LocalDateTime lastUpdated = LocalDateTime.of(2025, 10, 5, 6, 23, 32);
        portfolio = new Portfolio("Brad", 10000.00, lastUpdated);
    }

    @Test
    public void testConstrutor() {
        assertEquals("Brad", portfolio.getOwner());
        assertEquals(10000.00, portfolio.getCashBalance());
        assertEquals(0.0, portfolio.getPortfolioValue());
        assertEquals(0, portfolio.getHoldings().size());
    }

    @Test
    public void testBuyShareNullStock() {
        portfolio.buyShare(nullStock, 3.0);
        assertTrue(portfolio.getHoldings().isEmpty());
    }

    @Test
    public void testBuyTwiceUpdatesWeightedAverage() {
        portfolio.buyShare(nvidia, 10); // 10 @ 150
        nvidia.updateCurrentPrice(200);
        portfolio.buyShare(nvidia, 5); // 5 @ 200
        double avg = portfolio.getHoldings().get(NVDA).getAveragePrice();
        assertEquals((10 * 150 + 5 * 200) / 15.0, avg, 1e-3);
    }

    @Test
    public void testBuyShareWhichNotInPortfolio() {
        assertFalse(portfolio.getHoldings().containsKey(NVDA));
        portfolio.buyShare(nvidia, 3.0);
        assertEquals(10000.0 - 150.0 * 3.0, portfolio.getCashBalance());
        assertEquals(150.0 * 3.0, portfolio.getPortfolioValue());
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
    }

    @Test
    public void testBuyShareWhichInPortfolio() {
        portfolio.buyShare(nvidia, 3.0);
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
        portfolio.buyShare(nvidia, 3.0);
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
        assertEquals(10000.0 - 150.0 * 6.0, portfolio.getCashBalance());
        assertEquals(150.0 * 6.0, portfolio.getPortfolioValue());
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
    }   
    
    @Test
    public void testBuyShareWhichExceedCashBalanace() {
        assertFalse(portfolio.getHoldings().containsKey(NVDA));
        portfolio.buyShare(nvidia, 100.0);
        assertFalse(portfolio.getHoldings().containsKey(NVDA));
        assertEquals(10000.0, portfolio.getCashBalance());
        assertEquals(0.0, portfolio.getPortfolioValue());
    }

    @Test
    public void testSellShareNullStock() {
        portfolio.sellShare("", 2.0);
        assertTrue(portfolio.getHoldings().isEmpty());
    }

    @Test
    public void testSellShareNegativeQuantity() {
        portfolio.buyShare(nvidia, 3.0);
        portfolio.sellShare(NVDA, -4.0);
        assertTrue(portfolio.getHoldings().containsKey(nvidia.getSymbol()));
    }

    @Test
    public void testSellShareHoldingNotExist() {
        assertTrue(portfolio.getHoldings().isEmpty());
        portfolio.sellShare("FAKE", 5.0);
        assertTrue(portfolio.getHoldings().isEmpty());
        assertEquals(10000.0, portfolio.getCashBalance());
        assertEquals(0.0, portfolio.getPortfolioValue());
    }

    @Test
    public void testSellShareQuantityLargerThanShares() {
        portfolio.buyShare(nvidia, 3.0);
        portfolio.sellShare(NVDA, 5.0);
        assertTrue(portfolio.getHoldings().containsKey(nvidia.getSymbol()));
        assertEquals(3.0, portfolio.getHoldings().get(NVDA).getShares());
    }

    @Test
    public void testSellAllShareForOneHolding() {
        portfolio.buyShare(nvidia, 10.0);
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
        assertEquals(10000.0 - 150.0 * 10.0, portfolio.getCashBalance());
        assertEquals(150.0 * 10.0, portfolio.getPortfolioValue());
        portfolio.sellShare(NVDA, 10.0);
        assertFalse(portfolio.getHoldings().containsKey(NVDA));        
        assertEquals(10000.0, portfolio.getCashBalance());
        assertEquals(0.0, portfolio.getPortfolioValue());
    }

    @Test
    public void testSellSomeShareForOneHolding() {
        portfolio.buyShare(nvidia, 10.0);
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
        assertEquals(10000.0 - 150.0 * 10.0, portfolio.getCashBalance());
        assertEquals(150.0 * 10.0, portfolio.getPortfolioValue());
        portfolio.sellShare(NVDA, 5.0);
        assertTrue(portfolio.getHoldings().containsKey(NVDA));        
        assertEquals(10000.0 - 150.0 * 5.0, portfolio.getCashBalance());
        assertEquals(150.0 * 5.0, portfolio.getPortfolioValue());
    }

    @Test
    public void testAverageResetsAfterFullSell() {
        portfolio.buyShare(nvidia, 3);
        portfolio.sellShare(NVDA, 3.0);
        assertFalse(portfolio.getHoldings().containsKey(NVDA));
        // If you re-buy, avg should be fresh
        nvidia.updateCurrentPrice(123);
        portfolio.buyShare(nvidia, 1);
        assertEquals(123.0, portfolio.getHoldings().get("NVDA").getAveragePrice(), 1e-9);
    }   
    
    @Test
    public void testRecordsBuyAndSellTransactions() {
        portfolio.buyShare(nvidia, 2);
        portfolio.sellShare(NVDA, 1);
        List<Transaction> transactions = portfolio.getTransactionManager().getTransactions();
        assertEquals(2, transactions.size());
        assertEquals("BUY",  transactions.get(0).getAction());
        assertEquals("SELL", transactions.get(1).getAction());
        assertEquals("NVDA", transactions.get(0).getSymbol());
    }    

    @Test
    public void testPortfolioValueReflectsPriceChanges() {
        portfolio.buyShare(nvidia, 2); // price 150
        nvidia.updateCurrentPrice(300); // double
        portfolio.calculatePortfolioValue();
        assertEquals(2 * 300.0, portfolio.getPortfolioValue(), 1e-3);
    }
    
    @Test
    public void testLastUpdatedChangesOnBuySell() {
        LocalDateTime before = portfolio.getLastUpdated();
        portfolio.buyShare(nvidia, 1);
        LocalDateTime afterBuy = portfolio.getLastUpdated();
        assertTrue(afterBuy.isAfter(before));
        portfolio.sellShare(NVDA, 1);
        LocalDateTime afterSell = portfolio.getLastUpdated();
        assertTrue(afterSell.isAfter(afterBuy));
    }

    @Test
    public void testPortfolioToString() {
        portfolio.buyShare(nvidia, 3.0);
        String portfolioString = "";
        portfolioString += "================================ Portfolio ==================================\n";
        portfolioString += "Owner Name: Brad\n";
        portfolioString += "Cash Balance: $9550.00\n";
        portfolioString += "Portfolio Value: $450.00\n";
        portfolioString += "| Symbol | Current Price | Average Price | Shares | Market Value | Profit/Loss |\n";
        portfolioString += "| NVDA | $150.00 | $150.00 | 3.00 | $450.00 | $0.00 |\n";
        System.out.println(portfolioString.toString());
        System.out.println(portfolioString);
        assertEquals(portfolioString, portfolio.toString());
    }

    
}
