package org.sodeja.gabriel;

import org.sodeja.math.Rational;

public class Tak {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Object obj = tak(new Rational(18), new Rational(12), new Rational(6));
		long end = System.currentTimeMillis();
		System.out.println("(" + (end - start) + ")>" + obj);		
	}

	private static Rational tak(Rational x, Rational y, Rational z) {
		if(! (y.compareTo(x) < 0)) {
			return z;
		}
		Rational x1 = tak(x.substract(new Rational(1)), y, z);
		Rational y1 = tak(y.substract(new Rational(1)), z, x);
		Rational z1 = tak(x.substract(new Rational(1)), x, y);
		return tak(x1, y1, z1);
	}
}
