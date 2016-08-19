CREATE VIEW IF NOT EXISTS usa_sightings (sighted, reported, shape, state)
AS SELECT t1.sighted, t1.reported, t1.shape, t2.full_name
FROM ufodata t1 JOIN states t2
ON (LOWER(t2.abbreviation) = LOWER(SUBSTR( t1.sighting_location, (LENGTH(t1.sighting_location)-1)))) ;
