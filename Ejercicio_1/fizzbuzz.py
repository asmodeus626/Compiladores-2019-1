#FIZZBUZZ hata 30

contador = 1
while(contador < 30):
	if contador % 3 == 0:
		print "fizz"

	if contador % 5 == 0:
		print "buzz"
	
	if contador % 5 != 0 or contador % 3 != 0:
		print contador

	contador = contador + 1