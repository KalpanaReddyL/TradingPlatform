The trading application is developed with below assumptions
1. A file with multiple instructions. Each line has an instruction data with instruction id, asset name, Trading type, forex rate, currency, instruction date, value date, units and price with comma separated data.
2. Each instruction might have different trading currency but the trading exchange is in US and hence all the trades are settled in US trading centre.
3. Even if the trade is initiated in AED or SAR on sunday, the trade is settled in US on monday
4. Amount is negative values for Sell instructions and positive for buy instructions.
5. Junit has proper testing of the code and the main application with main method is just a prototype created in case live files are read.
6. Since loggers needs jars and since Junit4 uses 2 jars, due to constraints raised by the coding exercise, logging has been skipped. Logging is replaced with System
7. Currently for testing purpose only Junit is used due to constraints in dummy test data files.
8. An important point is a new field is introduced in this design as instruction id to differentiate between different instructions.
