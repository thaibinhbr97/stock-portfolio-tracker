# Stock Portfolio Application

The **Stock Portfolio Application** is a desktop program designed to help users manage and analyze their stock investments.  
It allows users to create and maintain a portfolio, record transactions such as buying and selling, and track the value of their holdings over time.  

Built using **Java with Object-Oriented Programming (OOP)** principles and a **Graphical User Interface (GUI)**, this application combines both usability and strong software design.  
In addition to managing stocks, it also provides simple **recommendations** based on stock performance and portfolio trends, making it a practical tool for investment tracking and a great project for reinforcing software engineering concepts.

### What will the application do?
- Allow users to buy, sell, and view stocks in their portfolio.
- Allow users to view their holdings and profit/loss in their portfolio.
- Record buy/sell transactions and maintain a history of investments.
- Filter transactions by action(buy/sell), date and time, or stock symbol.

### Who will use it?
- Individual investors who want to track their holdings.  
- Students and developers interested in learning OOP and GUI programming with a practical finance-related project.
- Anyone who wants a lightweight tool to simulate and manage a stock portfolio.  

### Why is this project of interest to me?
- This project is valuable because it bridges the gap between **software engineering concepts** and a **real-world application domain**—investing.  
- It provides an opportunity to practice **object-oriented design, GUI development, and data management** while creating a tool that mirrors how real investors track and analyze portfolios.  
- It’s also personally interesting because investing and finance are practical areas where technology directly impacts decision-making and user experience.

### User Stories
#### Holding
- As a user, I want to buy/sell holding of a stock including fields such as stock symbol, sector, company name, shares, current stock price.
- As a user, I want to view a market value of my holding.
- As a user, I want to see my unrealized profit/loss of my holding.

#### Portflio
- As a user, I want to use my cash balance when buy/sell shares of a stock in my portfolio.
- As a user, I want to view my current portfolio value.
- As a user, I want to view my unrealized profit/loss of my entire portfolio.

#### TransactionManager
- As a user, I want to view a record of stocks that I buy/sell in transaction manager including date time, stock symbol, action, share, purchase price, total.
- As a user, I want to filter a transaction history of stocks by all transactions, only buy or only sell, symbol, or date time range.

### Data Persistence
- As a user, I want to be able to have an option to save my entire state of stock portfolio including transaction history to file (if I choose).
- As a user, I want to be able to have an option to load my entire state of stock portfolio including transaction history from file (if I choose).

# Instructions for End User
- You can view the current stock market on the left panel called Market. You can view the portfolio on the right panel called Portfolio. You can view the transaction history on the bottom panel called Transaction History. You can view your information on the bottom left panel called Account Information. You can save the state of your application by clicking the save button at the bottom of the application. You can load the state of your application by clicking the load button at the bottom of the application. You can also filter Transaction History by action, symbol, or date time range by toggle between different options and input required fields.
- You can view the panel that displays the portfolio's stocks by buying/selling stocks. The display of the portfolio panel is automatically updated after each buy/sell action. Price on the portfolio panel can also be updated after updating the price of a stock.
- You can view the panel that displays the transaction history of buying/selling stocks. The display of the transaction history panel is automatically updated after each buy/sell action.
- You can locate my visual component by running the application on start.
- You can save the state of my application by clicking the save button at the bottom of the application.
- You can reload the state of my application by clicking the load button at the bottom of the application.
