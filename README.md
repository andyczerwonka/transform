esi.manage Measure Export to esi.portfolio Inventory
====================================================

Take an esi.manage measure export and transform it into something we can import into esi.portfolio

When defining a column in the measure export (define as many as you like), you can encode the title with attributes that you want to capture in esi.portfolio that may not be available in esi.manage.

#### Example Measure Export
```
-d localhost:1433/em
-h
-g PROJECT.NAME
-g "CUSTOM=Investment Type"
-g "CUSTOM=Campaign"
-g "CUSTOM=Rig Job"

-c "Measure:Production|Product:Oil|Unit:bbl" -m production -t Forecast -e "Gross Unrisked Raw" -U "Oil:bbl" -column-filter "PRODUCT:Oil" -consolidation "Default:Default"
-c "Measure:Capex|Unit:USD" -m expenditure -t Forecast -e "Unrisked Gross" -U USD -X DefaultF

2014-01-01 30 M "Evergreen Forecase"
```

This case we ask for two columns and we encode the required information in the column names. Because esi.portfolio has no notion of measure types (Production, Capex, etc), that information must also be encoded.

### Running the Utility
Download `met_2.11-1.0-one-jar.jar` from the `dist` folder. One you have run a measure export, take the output file run the transformation.
```
java -jar met_2.11-1.0-one-jar.jar measureexportresults.csv
```

If you want to generate a CSV file, simply pipe the results to a file.
```
java -jar met_2.11-1.0-one-jar.jar measureexportresults.csv > esi.portfolio.csv
```

### Limitations 
* esi.portfolio only imports `.xlsx` at the time being. For now you will have to open up the file in Excel and save it as an `.xlsx` file. 
* This is a stop-gap solution and therefor it does a minimal amount of validation. You won't get nice errors, you'll get stack traces or bad import files.
* It strips out series with all zeros




