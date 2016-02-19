# Super Simple Stock Market

This repo contains my implementation of the super simple stock exchange exercise. It is written in java using eclipse as the IDE and maven as the build and dependency management.

Instructions on building it and running it are mentioned below along with a sample output. 

## Design
The system is written around 4 interfaces
1. StockManager - It is initialized with a set of all stocks that can be traded on, and manages the dividends as well (Implemented in memory currently)
2. TradeTracker - It keeps tracks of all trades on stocks and can return recent trades (Implemented in memory currently)
3. StockAnalyser - It uses the data from the stock manager and trade tracker to calculate the required stock metrics
4. IndexCalculator - It abstracts away the index calculation (with only one implementation currently)

## Build and Execution: 
### Get code
Check out code using GIT or simply download the zip file from GitHub https://github.com/candicedsouza/jpmorgan-interview/archive/master.zip

### To run the demo
#### In Eclipse:  (Assuming you have a plugin to integrate with maven)
1. Create a workspace in eclipse
2. Check out code to that folder
3. Import the project into eclipse 
4. Rightclick on MainSystem.java in com.candicedsouza.stockexchange.demo package
5. Click on run as Java Application.

####From Console (This assumes you have maven setup):
1. Go to directory where you check it out (i.e. the directory in which the pom file exists)
2. Run mvn clean install
This will build as well as run the unit tests
3. From the same directory: java -cp target\classes\ com.candicedsouza.stockexchange.demo.MainSystem

###This will output the following demo:
	Initializing stock exchnage with 5 stocks
	Simulating stock trading on the exchange
	Analysis for stock : POP at price : 35.0
	Dividend Yield : 0.22857142857142856
	PE Ratio : 4.375
	List of trades in last 24 hours
	Trade [timestamp=Fri Feb 19 19:56:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 19:57:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 19:58:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:00:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:01:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:02:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:03:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:04:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:07:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 09:10:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 18:10:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 17:10:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Calculated Volume Weighted Stock Price based on trades in past 5 minutes :40.0
	Analysis done on one COMMON stock, POP @ price 28.56
	Analysis for stock : POP at price : 28.56
	Dividend Yield : 0.2801120448179272
	PE Ratio : 3.57
	List of trades in last 24 hours
	Trade [timestamp=Fri Feb 19 19:56:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 19:57:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 19:58:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:00:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:01:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:02:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:03:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:04:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:07:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 09:10:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 18:10:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 17:10:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Calculated Volume Weighted Stock Price based on trades in past 5 minutes :40.0
	Analysis done on one PREFERRED stock, GIN @ price 8.571
	Analysis for stock : GIN at price : 8.571
	Dividend Yield : 23.33450005833625
	PE Ratio : 1.071375
	List of trades in last 24 hours
	Trade [timestamp=Fri Feb 19 20:10:26 GMT 2016, quantity=300, type=BUY, price=45.86]
	Calculated Volume Weighted Stock Price based on trades in past 5 minutes :45.86
	Displaying all trades done in the last 5 minutes
	All trades in last : 5 MINUTES
	Trade [timestamp=Fri Feb 19 20:07:26 GMT 2016, quantity=80, type=BUY, price=40.0]
	Trade [timestamp=Fri Feb 19 20:10:26 GMT 2016, quantity=300, type=BUY, price=45.86]
	Trade [timestamp=Fri Feb 19 20:10:26 GMT 2016, quantity=300, type=BUY, price=23.89]
	Trade [timestamp=Fri Feb 19 20:10:26 GMT 2016, quantity=50, type=SELL, price=10.78]
	Trade [timestamp=Fri Feb 19 20:10:26 GMT 2016, quantity=300, type=BUY, price=45.86]
	Trade [timestamp=Fri Feb 19 20:10:26 GMT 2016, quantity=280, type=SELL, price=65.86]
	Trade [timestamp=Fri Feb 19 20:10:26 GMT 2016, quantity=300, type=BUY, price=45.86]
	Computing index of trade market : 40.03318551295543
