package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
 * Represents user's overall stock portfolio.
 * The Portfolio maintains a collection of holdings and provides
 * methods for buying, selling, showcase  as well as calculating the total value of all owned stocks.
 */
public class Portfolio { 
    private String ownerName;
    private double cashBalance;
    private double portfolioValue;
    private LocalDateTime lastUpdated;
    private TransactionManager transactionManager;

    private Map<String, Holding> holdings;
    
    // EFFECTS: constructs a Portfolio with owner name, cash balance, 
    // portfolioValue initilized to 0.0, lastUpdated initialized to LocalDateTime.now(), 
    // holdings initialized to HashMap<>() to keep track of key-value pairs String symbol: Holding holding, initialize
    // TransactionManager object
    public Portfolio(String ownerName, double cashBalance, LocalDateTime lastUpdated) {
        this.ownerName = ownerName;
        this.cashBalance = cashBalance;
        this.portfolioValue = 0;
        this.lastUpdated = LocalDateTime.now();
        this.holdings = new HashMap<>();
        this.transactionManager = new TransactionManager();
    }

    // REQUIRES: stock != null, quantity > 0, total price (stock current price * quantity) <= cashBalance
    // MODIFIES: this, Holding, TransactionManager
    // EFFECTS: buy shares of a stock, update portfolio value and cash balance for owner once action is done.
    public void buyShare(Stock stock, double quantity) {
        if (stock == null || quantity <= 0) {
            return;
        }

        double totalPrice = stock.getCurrentPrice() * quantity;
        if (totalPrice > cashBalance) {
            return;
        }

        String symbol = stock.getSymbol();
        Holding holding = holdings.get(symbol);
        if (holding == null) {
            holding = new Holding(stock, quantity);
            holdings.put(symbol, holding);
        } else {
            holding.buyShare(quantity);
        }

        cashBalance -= totalPrice;
        calculatePortfolioValue();
        lastUpdated = LocalDateTime.now();

        // create and record buy transaction
        Transaction transaction = new Transaction(symbol, "BUY", quantity, stock.getCurrentPrice(), lastUpdated);
        transactionManager.addTransaction(transaction);
    }

    // REQUIRES: shares > 0, quantity <= current shares for this stock holding
    // MODIFIES: this, Holding, TransactionManager
    // EFFECTS: sell shares of a stock symbol with stock's current price. 
    // Update portfolio value and cash balance for owner once action is done.
    public void sellShare(String symbol, double quantity) {
        if (symbol == null || quantity < 0) {
            return;
        }
        Holding holding = holdings.get(symbol);
        if (holding == null || quantity > holding.getShares()) {
            return; // no holding exits yet or not enough shares to sell
        }

        double currentPrice = holding.getStock().getCurrentPrice();
        double totalProceeds = currentPrice * quantity;

        holding.sellShare(quantity);
        cashBalance += totalProceeds;

        if (holding.getShares() == 0) {
            holdings.remove(symbol);
        }

        calculatePortfolioValue();
        lastUpdated = LocalDateTime.now();

        // create and record sell transaction
        Transaction transaction = new Transaction(symbol, "SELL", quantity, currentPrice, lastUpdated);
        transactionManager.addTransaction(transaction);

    }

    // MODIFIES: this, Transaction, Holding
    // EFFECTS: recalculates and updates portfolioValue once a successful BUY/SELL action occurs.
    // (shares * current stock price)
    public void calculatePortfolioValue() {
        double total = 0.0;
        for (Holding h : holdings.values()) {
            total += h.getStock().getCurrentPrice() * h.getShares();
        }
        this.portfolioValue = total;
    }

    // getters 
    public double getCashBalance() {
        return cashBalance;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public Map<String, Holding> getHoldings() {
        return holdings;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public String getOwner() {
        return ownerName;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
}    

    // EFFECTS: returns portfolio information with owner name, cash balance, and portfolio.
    // with the format as below:
    // Owner name: Brad
    // Cash balance: $10000
    // Portfolio Value: $0
    public String getPortfolioInformation() {
        String portfolioInformationString = String.format("Owner Name: %s\nCash Balance: $%.2f\nPortfolio Value: $%.2f",
                ownerName,
                cashBalance,
                portfolioValue
        );
        return portfolioInformationString;
    }

    // EFFECTS: returns a header as a below format
    // |   Symbol   |          Current Price          |          Average Price          |          Shares          |          Market Value         |          Profit/Loss         |
    public String getHeader() {
        String headerString = String.format("| %s | %s | %s | %s | %s | %s |",
                "Symbol", "Current Price", "Average Price", "Shares", "Market Value", "Profit/Loss");
        return headerString;
    }    

    // EFFECTS: Overriding toString() method of Portfolio class
    // with the format as an example below:
    // ================================ Portfolio ==================================
    // Owner name: Brad
    // Cash Balance: $10000
    // | Symbol |          Current Price          |          Average Price          |          Shares          |          Market Value         |          Profit/Loss         |
    // |  AAPL  |            $100.00              |            $150.00              |            2.00          |            $300.00            |            +$100.00          |
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("================================ Portfolio ==================================\n");
        sb.append(getPortfolioInformation()).append("\n");
        sb.append(getHeader()).append("\n");
        for (Holding h : holdings.values()) {
            sb.append(h.toString()).append("\n");
        }
        return sb.toString();
    }
}
