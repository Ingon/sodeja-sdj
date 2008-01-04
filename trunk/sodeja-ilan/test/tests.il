class Rational {
	fun Rational num den = {
		@num = num
		@den = den
	}
	
	fun * other = {
		tnum = @num * (other num)
		tden = @den * (other den)
		Rational new tnum tden
	}

	fun + other = {
		tnum = (@num + other den) + (@den + other num)
		tden = @den * other num
		Rational new tnum tden
	}
	
	fun num = @num
	
	fun den = @den
}

f = Rational new 1 2
s = Rational new 2 3
f * s

class Rational {
	fun / other = {
		tnum = @num * other den
		tden = @den * other num
		Rational new tnum tden
	}
}

