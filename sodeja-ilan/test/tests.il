class Pair {
	fun Pair f s = {
		@f = f
		@s = s
	}

	fun first = @f
	
	fun second = @s
}

class Rational {
	fun Rational num den = {
		@num = num
		@den = den
	}
	
	fun * other = {
		tnum = @num * other num
		tden = @den * other den
		Rational new tnum tden
	}

	fun + other = {
		tnum = (@num + other den) + (@den + other num)
		tden = @den * other num
	}
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

a

3 + 5

fun + a b = a + b

fun * a b = a * b
	
+ 6 8

* 4 8

