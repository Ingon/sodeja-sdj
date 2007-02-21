package org.sodeja.sdj;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Supercombinator;

public class PrettyPrinterTest extends TestCase {
	public void testPrinting() {
		CoreProgram program = new CoreProgram(new ArrayList<Supercombinator<Name>>());
		PrettyPrinter.print(program);
		fail("Not yet implemented");
	}
}
