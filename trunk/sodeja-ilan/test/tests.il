class Pair {
	def Pair f s = {
		@f = f
		@s = s
	}

	def first = @f
	
	def second = @s
}

class Rational {
	def Rational num den = {
		@num = num
		@den = den
	}
	
	def * other = {
		tnum = @num * other num
		tden = @den * other den
		Rational new tnum tden
	}

	def + other = {
		tnum = (@num + other den) + (@den + other num)
		tden = @den * other num
	}
}

f = Rational new 1 2
s = Rational new 2 3
f * s

class Rational {
	def / other = {
		tnum = @num * other den
		tden = @den * other num
		Rational new tnum tden
	}
}

3 + 5

def + a b = a + b

def * a b = a * b
	
+ 6 8

* 4 8


