# ShoppingBasketPricer
A java project, written in TDD, that simulates a shopping basket, and price with given item price list and special offers.  
  
  
#### How to build ??  
its a maven project, so a mvn clean install is enough  
  
  
#### How to package ??  
mvn clean package  
  
  
#### How to run ??  
Simply run the jar file like below, by giving as argument the item list you want to put in basket.  
the pricing will be done according to default prices and offers given in the exercise description  
  
ex: java -jar shoppingBasket-0.0.1.jar Apples Milk Apples Bread ...
  
  
#### How to configure ??  
if you want to configure the price list and the special offers available, you can give as system properties the file paths  
  
ex: java -jar -DshoppingBasket.pricer.priceList=./priceList.properties -DshoppingBasket.pricer.offerList=./offersList.properties shoppingBasket-0.0.1.jar Apples Soup Bread ...  
