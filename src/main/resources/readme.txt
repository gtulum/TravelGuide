This REST API calculates how many exact times can we go through all neighbor countries within our total budget. 
It calculates the budget for each country in their respected currencies. 
If the exchange rate is missing it returns the amount in the original currency. 
The potential leftover amount from the total budget is returned in the original currency. 

It accepts the following request parameters:

countryCode => Starting country
budget      => Budget per country (equal for all neighbor countries) 
totalBudget => Total budget 
curr        => Currency of the total budget
 

Example:
Request values:
•	Starting country: Bulgaria (BG)
•	Budget per country: 100
•	Total budget: 1200
•	Currency: EUR

Example Request :  http://localhost:8080/getTravelingInfo/BG/100/1200/EUR

Example Result:
Bulgaria has 5 neighbor countries (TR, GR, MK, SR, RO) and we can travel around them 2 times. 
We will have 200 EUR leftover. For Turkey we will need to buy "1539.34 TRY", for Romania we will need to buy "966.699" RON, and so on. 

	  