```mermaid
classDiagram
    %% Model Package
    class Event
    class EventLog
    class Holding
    class Market
    class MarketCatalog
    class Portfolio
    class Stock
    class Transaction
    class TransactionManager

    %% Persistence Package
    class Writable
    class JsonReader
    class JsonWriter

    %% UI Package
    class StockPortfolioApp
    class MainFrame
    class SplashScreen
    class AccountInfoPanel
    class BuyStockPanel
    class MarketPanel
    class PortfolioPanel
    class SaveLoadPanel
    class SellStockPanel
    class TransactionHistoryPanel
    class UpdateStockPanel

    %% Relationships: Interfaces
    Portfolio ..|> Writable
    Stock ..|> Writable
    Holding ..|> Writable
    Market ..|> Writable
    Transaction ..|> Writable

    %% Relationships: Model Composition & Aggregation
    EventLog *-- "0..*" Event
    Portfolio *-- "0..*" Holding
    Portfolio *-- "1" TransactionManager
    TransactionManager *-- "0..*" Transaction
    Market o-- "0..*" Stock
    Holding --> "1" Stock

    %% Relationships: UI -> Model & Persistence
    StockPortfolioApp --> Portfolio
    StockPortfolioApp --> Market
    StockPortfolioApp --> JsonWriter
    StockPortfolioApp --> JsonReader

    MainFrame --> Portfolio
    MainFrame --> Market
    
    %% Relationships: UI Composition (Panels)
    MainFrame --> AccountInfoPanel
    MainFrame --> PortfolioPanel
    MainFrame --> MarketPanel
    MainFrame --> BuyStockPanel
    MainFrame --> SellStockPanel
    MainFrame --> UpdateStockPanel
    MainFrame --> TransactionHistoryPanel

    %% Relationships: Panel Dependencies
    AccountInfoPanel --> Portfolio
    
    BuyStockPanel --> Portfolio
    BuyStockPanel --> Market
    BuyStockPanel --> PortfolioPanel
    BuyStockPanel --> MarketPanel
    BuyStockPanel --> AccountInfoPanel
    
    MarketPanel --> Market
    
    PortfolioPanel --> Portfolio
    
    SaveLoadPanel --> Portfolio
    SaveLoadPanel --> Market
    SaveLoadPanel --> PortfolioPanel
    SaveLoadPanel --> MarketPanel
    SaveLoadPanel --> TransactionHistoryPanel
    SaveLoadPanel --> AccountInfoPanel
    
    SellStockPanel --> Portfolio
    SellStockPanel --> Market
    SellStockPanel --> PortfolioPanel
    SellStockPanel --> MarketPanel
    SellStockPanel --> AccountInfoPanel
    
    TransactionHistoryPanel --> Portfolio
    
    UpdateStockPanel --> Market
    UpdateStockPanel --> MarketPanel
    UpdateStockPanel --> AccountInfoPanel
```
