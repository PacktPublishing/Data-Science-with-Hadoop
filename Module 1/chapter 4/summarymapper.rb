#!/usr/bin/env ruby

while line = gets
	puts "total\t1"
	parts = line.split("\t")
	puts "badline\t1" if parts.size != 6
	puts "sighted\t1" if !parts[0].empty?
	puts "recorded\t1" if !parts[1].empty?
	puts "location\t1" if !parts[2].empty?
	puts "shape\t1" if !parts[3].empty?
	puts "duration\t1" if !parts[4].empty?
	puts "description\t1" if !parts[5].empty?
end
