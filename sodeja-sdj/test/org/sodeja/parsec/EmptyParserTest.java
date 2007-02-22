package org.sodeja.parsec;

import java.util.List;

import junit.framework.TestCase;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;

public class EmptyParserTest extends TestCase {
	public void testExecute() {
		List<String> tokens = ListUtils.asList("1", "2");
		
		EmptyParser<String, String> parser = new EmptyParser<String, String>("Test", null);
		List<Pair<String, List<String>>> result = parser.execute(tokens);
		
		assertNull(result);
		assertEquals(2, tokens.size());
	}
}
