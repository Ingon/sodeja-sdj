package org.sodeja.ilan.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Lexer {

	public static List<Datum> tokenize(String str) throws IOException {
		return tokenize(new StringReader(str));
	}
	
	public static List<Datum> tokenize(InputStream is) throws IOException {
		return tokenize(new InputStreamReader(is));
	}
	
	public static List<Datum> tokenize(Reader r) throws IOException {
		PushbackReader pr = new PushbackReader(r);
		List<Datum> datums = new ArrayList<Datum>();
		
		readDatums(pr, datums);
		
		return datums;
	}

	private static void readDatums(PushbackReader pr, List<Datum> datums) throws IOException {
		Character ch = null;
		while(( ch = read(pr)) != null) {
			Datum datum = readDatum(pr, ch);
			if(datum == null) {
				return;
			}
			
			datums.add(datum);
		}
	}
	
	private static Datum readDatum(PushbackReader pr, Character ch) throws IOException {
		if(Character.isWhitespace(ch)) {
			ch = readWhiteSpaces(pr);
			if(ch == null) {
				return null;
			}
		}
		
		if(ch == ';') {
			ch = readComment(pr);
		}
		
		if(ch == '\'' || ch == '`' || ch == ',') {
			return readAbbrevation(pr, ch);
		}
		
		if(isIdentifierStart(ch)) {
			return readIdentifier(pr, ch);
		}
		
		if(Character.isDigit(ch)) {
			return readNumber(pr, ch);
		}
		
		if(ch == '"') {
			return readString(pr, ch);
		}
		
		if(ch == '(' || ch == '[') {
			return readList(pr, ch);
		}
		
		if(ch == '#') {
			ch = read(pr);
			if(ch == 't' || ch == 'T' || ch == 'f' || ch == 'F') {
				Character next = read(pr);
				if(! isDelimiter(next)) {
					throw new IllegalArgumentException("Unknown syntax with starting as boolean");
				}
				unread(pr, next);
				if(ch == 't' || ch == 'T') {
					return new BooleanDatum(Boolean.TRUE);
				} else {
					return new BooleanDatum(Boolean.FALSE);
				}
			}
			
			if(ch == '\\') {
				ch = read(pr);
				// TODO now we are supporting one char only
				Character next = read(pr);
				if(! isDelimiter(next)) {
					throw new IllegalArgumentException("Unknown syntax with starting as boolean");
				}
				unread(pr, next);
				
				return new CharacterDatum(ch);
			}
			
//			if(ch == '(') {
//				VectorDatum datum = new VectorDatum();
//				readDatums(pr, datum);
//				
//				Character end = read(pr);
//				if(ch == '(' && end != ')') {
//					throw new RuntimeException("Illeagal lexical structure start with " + ch + " but ends with " + end);
//				} else if(ch == '[' && end != ']') {
//					throw new RuntimeException("Illeagal lexical structure start with " + ch + " but ends with " + end);
//				}
//				
//				return datum;
//			}
			
			throw new UnsupportedOperationException("Not supported");
		}
		
		unread(pr, ch);
		return null;
	}
	
	private static boolean isIdentifierStart(Character ch) {
		return Character.isJavaIdentifierStart(ch) || !(
				Character.isDigit(ch) || isDelimiter(ch));
	}

	private static Datum readIdentifier(PushbackReader pr, Character initial) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(initial);
		
		Character ch = null;
		while((ch = read(pr)) != null) {
			if(isDelimiter(ch)) {
				unread(pr, ch);
				break;
			}
			sb.append(ch);
		}
		
		return new IdentifierDatum(sb.toString());
	}
	
	private static Datum readNumber(PushbackReader pr, Character initial) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(initial);
		
		Character ch = null;
		while((ch = read(pr)) != null) {
			if(isDelimiter(ch)) {
				unread(pr, ch);
				break;
			}
			sb.append(ch);
		}
		
		return new NumberDatum(Integer.parseInt(sb.toString()));
	}

	private static Datum readString(PushbackReader pr, Character initial) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		Character ch = null;
		while((ch = read(pr)) != null) {
			if(ch == initial) {
				break;
			}
			sb.append(ch);
		}
		
		return new StringDatum(sb.toString());
	}

	private static Datum readList(PushbackReader pr, Character ch) throws IOException {
		ListDatum datum = new ListDatum();
		readDatums(pr, datum);
		
		Character end = read(pr);
		if(ch == '(' && end != ')') {
			throw new RuntimeException("Illeagal lexical structure start with " + ch + " but ends with " + end);
		} else if(ch == '[' && end != ']') {
			throw new RuntimeException("Illeagal lexical structure start with " + ch + " but ends with " + end);
		}
		
		return datum;
	}

	private static Datum readAbbrevation(PushbackReader pr, Character ch) throws IOException {
		String str = "" + ch;
		ch = read(pr);
		if(ch == '@') {
			str += ch;
			ch = read(pr);
		}
		
		Datum datum = readDatum(pr, ch);
		ListDatum result = new ListDatum();
		
		if(str.equals("'")) {
			result.add(new IdentifierDatum("quote"));
		} else if(str.equals("`")) {
			result.add(new IdentifierDatum("quasiquote"));
		} else if(str.equals(",")) {
			result.add(new IdentifierDatum("unquote"));
		} else if(str.equals(",@")) {
			result.add(new IdentifierDatum("unquote-splicing"));
		} else {
			throw new RuntimeException("Unknown abbrevation!");
		}
		result.add(datum);
		return result;
	}
	
	private static boolean isDelimiter(Character ch) {
		return Character.isWhitespace(ch) || ch == '(' || ch == ')' 
			|| ch == '[' || ch == ']' || ch == '"' || ch == ';' || ch == '#';
	}
	
	private static Character readWhiteSpaces(PushbackReader pr) throws IOException {
		Character ch = null;
		while((ch = read(pr)) != null) {
			if(! Character.isWhitespace(ch)) {
				break;
			}
		}
		
		if(ch == null) {
			return null;
		}
		
		if(ch == ';') {
			return readComment(pr);
		}
		
		return ch;
	}

	private static Character readComment(PushbackReader pr) throws IOException {
		Character ch = null;
		while((ch = read(pr)) != null) {
			if(ch == '\n') {
				break;
			}
			
			if(ch == '\r') {
				Character next = read(pr);
				if(next == '\n') {
					break;
				}
				unread(pr, next);
				break;
			}
		}
		
		return readWhiteSpaces(pr);
	}
	
	private static Character read(PushbackReader reader) throws IOException {
		int value = reader.read();
		if(value == -1) {
			return null;
		}
		return (char) value;
	}
	
	private static void unread(PushbackReader reader, Character ch) throws IOException {
		if(ch == null) {
			return;
		}
		reader.unread(ch);
	}
}
