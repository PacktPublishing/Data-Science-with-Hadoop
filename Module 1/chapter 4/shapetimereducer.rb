#!/usr/bin/env ruby

current = nil
min = 0
max = 0
mean = 0
total = 0
count = 0

while line = gets
	word, time = line.split("\t")
	time = Integer(time)

	if word == current
		count = count+1
		total = total+time
		min = time if time < min
		max = time if time > max
	else
		puts current+"\t"+min.to_s+" "+max.to_s+" "+(total/count).to_s if current
		current = word
		count = 1
		total = time
		min = time
		max = time
	end
end
puts current+"\t"+min.to_s+" "+max.to_s+" "+(total/count).to_s
