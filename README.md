# Currency Exchange Rate Application
## Overview
This Java application retrieves exchange rates between currencies based on a CSV file input.
It handles requests for specific dates and currency pairs, providing the closest available rate if exact data are not present.

## Features
Parses a CSV file containing exchange rate data for all currencies in the year 2023.
Supports retrieval of exchange rates for any date and currency pair.
Handles missing data by returning the most recent available rate.
Provides error handling for invalid input and unsupported currency pairs.

## Installation
Prerequisites
Java Development Kit (JDK) 11 or higher installed

## Build from Source
Clone the repository:
git clone https://github.com/kerodotou01/currency-app

## Compile the Java source code:

1. Go to the path of the project: cd currency-app
2. Compile the java source files: javac -d out src/main/java/currency/*.java src/main/java/currency/CurrencyMain.java
3. Create the jar: cd out; jar cfe exchange-rate-finder.jar main.java.currency.CurrencyMain .

## Run the application:

Go to the path of the project and to the /out directory and run the below: 
java -jar exchange-rate-finder.jar

## Usage
Run the application and follow the command-line prompts to retrieve exchange rates for specific dates and currency pairs.
