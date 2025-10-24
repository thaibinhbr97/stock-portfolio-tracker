package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import model.Holding;
import model.Market;
import model.Portfolio;
import model.Stock;
import model.Transaction;

public abstract class TestJson {
    protected static final double EPSILON = 1e-9;
    protected static final LocalDateTime FIXED_TIME = LocalDateTime.of(2024, 1, 1, 12, 0);

    protected Market makeMarket() {
        Market m = new Market();
        m.addOrReplace(new Stock("AAPL", "Apple Inc.", "Technology", 180.00));
        m.addOrReplace(new Stock("AMZN", "Amazon.com, Inc.", "Consumer Discretionary", 130.00));
        m.addOrReplace(new Stock("MSFT", "Microsoft Corporation", "Technology", 330.00));
        return m;        
    }

    protected Portfolio makeEmptyPortfolio() {
        return new Portfolio("Brad", 10_000.00, FIXED_TIME);
    }

    protected Portfolio makePortfolioWithTrades(Market m) {
        Portfolio p = makeEmptyPortfolio();
        p.buyShare(m.getStock("AAPL"), 10.0); // @ 180
        m.getStock("AAPL").updateCurrentPrice(200.00); 
        p.buyShare(m.getStock("AAPL"), 5.0);  // @ 200
        p.buyShare(m.getStock("AMZN"), 20.0); // @ 130
        p.sellShare("AMZN", 5.0);             // @ 130
        return p;
    }

    protected void assertHolding(Holding h, String sym, double shares, double avg, double current) {
        assertNotNull(h);
        assertEquals(sym, h.getSymbol());
        assertEquals(shares, h.getShares(), EPSILON);
        assertEquals(avg, h.getAveragePrice(), EPSILON);
        assertEquals(current, h.getStock().getCurrentPrice(), EPSILON);
    }

    protected void assertTransaction(Transaction t, String symbol, String action,
                                     double shares, double price) {
        assertNotNull(t);
        assertEquals(symbol, t.getSymbol());
        assertEquals(action, t.getAction());
        assertEquals(shares, t.getShares(), EPSILON);
        assertEquals(price, t.getPrice(), EPSILON);
        assertEquals(shares * price, t.getTotal(), EPSILON);
        assertNotNull(t.getDateTime());
    }
}
