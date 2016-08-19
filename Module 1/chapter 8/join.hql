SELECT t1.sighted, t2.full_name
FROM ufodata t1 JOIN states t2
ON (lower(t2.abbreviation) = lower(substr( t1.sighting_location, (length(t1.sighting_location)-1)))) 
LIMIT 5 ;
