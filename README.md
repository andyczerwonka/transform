esi.manage Measure Export to esi.portfolio Inventory
====================================================

Take an esi.manage measure export and transform it into something we can import into esi.portfolio

When defining a column in the measure export (define as many as you like), you can encode the title with attributes that you want to capture in esi.portfolio that may not be available. Because esi.portfolio has no notion of measure types (Production, Capex, etc), that information can also be encoded.

### Example
```
"Measure:Production|Version:Forecast|Variant:Gross Raw|Product:Oil|Unit:bbl"    
```
