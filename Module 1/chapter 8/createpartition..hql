DROP TABLE partufo;
CREATE TABLE partufo(sighted string, reported string, sighting_location string,
shape string, duration string, description string) 
PARTITIONED BY (year string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t' ;
