require 'rubygems'
require 'avro'

file = File.open('sightings.avro', 'wb')
schema = Avro::Schema.parse(
File.open("ufo.avsc", "rb").read)

writer = Avro::IO::DatumWriter.new(schema)
dw = Avro::DataFile::Writer.new(file, writer, schema)
dw << {"sighting_date" => "2012-01-12", "city" => "Boston", "shape" => "diamond", "duration" => 3.5}
dw << {"sighting_date" => "2011-06-13", "city" => "London", "shape" => "light", "duration" => 13}
dw << {"sighting_date" => "1999-12-31", "city" => "New York", "shape" => "light", "duration" => 0.25}
dw << {"sighting_date" => "2001-08-23", "city" => "Las Vegas", "shape" => "cylinder", "duration" => 1.2}
dw << {"sighting_date" => "1975-11-09", "city" => "Miami", "duration" => 5}
dw << {"sighting_date" => "2003-02-27", "city" => "Paris", "shape" => "light", "duration" => 0.5}
dw << {"sighting_date" => "2007-04-12", "city" => "Dallas", "shape" => "diamond", "duration" => 3.5}
dw << {"sighting_date" => "2009-10-10", "city" => "Milan", "shape" => "formation", "duration" => 0}
dw << {"sighting_date" => "2012-04-10", "city" => "Amsterdam", "shape" => "blur", "duration" => 6}
dw << {"sighting_date" => "2006-06-15", "city" => "Minneapolis", "shape" => "saucer", "duration" => 0.25}
dw.close

