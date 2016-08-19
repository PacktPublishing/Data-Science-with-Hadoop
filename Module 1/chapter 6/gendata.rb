File.open("skipdata.txt", "w") do |file|
	3.times do
		500000.times{file.write("A valid record\n")}
		5.times{file.write("skiptext\n")}
	end
	500000.times{file.write("A valid record\n")}
end
