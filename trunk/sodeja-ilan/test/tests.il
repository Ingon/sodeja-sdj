3 + 5;

+ a b = a + b;

* a b = a * b;
	
+ 6 8; 

* 4 8;

class Pair {
	Pair f s = {
		@f = f;
		@s = s;
	}
	
	first = @f;
	
	second = @s;
}

class Rational {
	Rational num den = {
		@num = num;
		@den = den;
		@+ other = {
			tnum = (@num + other den) + (@den + other num);
			tden = @den * other num;
		}
	}
	
	* other = {
		tnum = @num * other num;
		tden = @den * other den;
		Rational new tnum tden;
	}
}

f = Rational new 1 2;
s = Rational new 2 3;
f * s;

class Rational {
	/ other = {
		tnum = @num * other den;
		tden = @den * other num;
		Rational new tnum tden;
	}
}
