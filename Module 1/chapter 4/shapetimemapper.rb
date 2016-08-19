#!/usr/bin/env ruby

pattern = Regexp.new /\d* ?((min)|(sec))/

while line = gets
	parts = line.split("\t")
	if parts.size == 6
		shape = parts[3].strip
		duration = parts[4].strip.downcase
		if !shape.empty? && !duration.empty?
			match = pattern.match(duration)
			time = /\d*/.match(match[0])[0]
			unit = match[1]
			time = Integer(time)
			time = time * 60 if unit == "min"
			puts shape+"\t"+time.to_s
		end
	end
end
