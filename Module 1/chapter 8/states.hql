DROP TABLE states ;
CREATE EXTERNAL TABLE states(abbreviation string, full_name string)
ROW FORMAT delimited
FIELDS TERMINATED BY '\t'
LOCATION '/tmp/states' ;
