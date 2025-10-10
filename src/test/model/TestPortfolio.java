package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPortfolio {
    // symbol strings
    private static final String ORCL = "ORCL";
    private static final String APPL = "AAPL";
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

    private Portfolio portfolio;
    
    @BeforeEach
    void runBefore() {
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
    void testConstrutor() {
        assertEquals("Brad", portfolio.getOwner());
        assertEquals(10000.00, portfolio.getCashBalance());
        assertEquals(0.0, portfolio.getPortfolioValue());
        assertEquals(0, portfolio.getHoldings().size());
    }

    @Test
    void testBuyShareWhichNotInPortfolio() {
        assertFalse(portfolio.getHoldings().containsKey(NVDA));
        portfolio.buyShare(nvidia, 3.0);
        assertEquals(10000.0 - 150.0 * 3.0, portfolio.getCashBalance());
        assertEquals(150.0 * 3.0, portfolio.getPortfolioValue());
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
    }

    @Test
    void testBuyShareWhichInPortfolio() {
        portfolio.buyShare(nvidia, 3.0);
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
        portfolio.buyShare(nvidia, 3.0);
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
        assertEquals(10000.0 - 150.0 * 6.0, portfolio.getCashBalance());
        assertEquals(150.0 * 6.0, portfolio.getPortfolioValue());
        assertTrue(portfolio.getHoldings().containsKey(NVDA));
    }   
    
    @Test
    void testBuyShareWhichExceedCashBalanace() {
        assertFalse(portfolio.getHoldings().containsKey(NVDA));
        portfolio.buyShare(nvidia, 100.0);
        assertFalse(portfolio.getHoldings().containsKey(NVDA));
        assertEquals(10000.0, portfolio.getCashBalance());
        assertEquals(0.0, portfolio.getPortfolioValue());
    }

    @Test
    void testSellAllShareForOneHolding() {
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
    void testSellSomeShareForOneHolding() {
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
    void testPortfolioToString() {
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
