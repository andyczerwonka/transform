esi.manage Measure Export to esi.portfolio Inventory
====================================================

Take an esi.manage measure export and transform it into something we can import into esi.portfolio

When defining a column in the measure export (define as many as you like), you must encode the title with attributes that you want to capture in esi.portfolio.

#### Example Measure Export Parameters
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

The above file asks for two columns. Within the column names we encode desired attributes that you may want to group and filter by. Because esi.portfolio has no notion of measure types (Production, Capex, etc), that information must also be encoded. Unit is also required.

### Running the Utility
Download `met_2.11-1.0-one-jar.jar` from the `dist` folder. Once you have run a measure export, take the output file run the transformation.
```
java -jar met_2.11-1.0-one-jar.jar <measureexportresults.csv> <number of periods>
```
### Assumptions
* Header is present, after all, that's where we get the metadata :)

### Limitations 
* esi.portfolio only imports `.xlsx` at the time being. For now you will have to open up the file in Excel and save it as an `.xlsx` file. 
* This is a stop-gap solution and therefor it does a minimal amount of validation. You won't get any nice errors if you get it wrong, you'll get stack traces or bad import files.
* "all zeros" series' are stripped out.
* No metric support




