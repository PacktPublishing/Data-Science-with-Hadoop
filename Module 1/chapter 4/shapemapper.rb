#!/usr/bin/env ruby

while line = gets
	parts = line.split("\t")

	if parts.size == 6 
	shape = parts[3].strip 
	puts shape+"\t1" if !shape.empty? 
	end
end
