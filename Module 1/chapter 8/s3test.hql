CREATE EXTERNAL TABLE ufodata(sighted string, reported string, sighting_location string,
shape string, duration string, description string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t' 
LOCATION '${INPUT}/ufo.tsv' ;

CREATE EXTERNAL TABLE states(abbreviation string, full_name string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LOCATION '${INPUT}/states.txt' ;

CREATE VIEW IF NOT EXISTS usa_sightings (sighted, reported, shape, state)
AS select t1.sighted, t1.reported, t1.shape, t2.full_name
FROM ufodata t1 JOIN states t2
ON (LOWER(t2.abbreviation) = LOWER(SUBSTR( t1.sighting_location, (LENGTH(t1.sighting_location)-1)))) ;

CREATE EXTERNAL TABLE state_results ( reported string, shape string, state string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t' ROWS TERMINATED BY '\n'
STORED AS TEXTFILE
LOCATION '${OUTPUT}/states'

INSERT OVERWRITE TABLE state_results
SELECT reported, shape, state
FROM usa_sightings
WHERE state = 'California' ;
