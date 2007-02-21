package org.sodeja.sdj.lexer;

public class Token {
	public final int line;
	public final String name;

	Token(final int line, final String name) {
		this.line = line;
		this.name = name;
	}

	Token(final int line, final Character ch) {
		this(line, String.valueOf(ch));
	}

	Token(final int line, final StringBuilder builder) {
		this(line, builder.toString());
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(! (obj instanceof Token)) {
			return false;
		}
		
		return this.name.equals(((Token) obj).name);
	}
}
