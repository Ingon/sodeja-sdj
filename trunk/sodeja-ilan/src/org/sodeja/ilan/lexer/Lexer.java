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
		
		Character ch = null;
		while(( ch = read(pr)) != null) {
			
		}
		
		return datums;
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
