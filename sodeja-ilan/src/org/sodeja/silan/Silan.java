package org.sodeja.silan;

import java.io.IOException;

import org.sodeja.files.FileUtils;
import org.sodeja.silan.gui.SilanContext;
import org.sodeja.silan.gui.SilanFrame;
import org.sodeja.swing.util.SwingUtils;

public class Silan {
	public static void main(String[] args) throws IOException {
//		VirtualMachine vm = new VirtualMachine();
//		String frameSource = FileUtils.readFully("test/frame.silan");
//		SILObject value = vm.compileAndExecute(frameSource);
//		System.out.println("Value: " + value);

		SilanContext ctx = new SilanContext();
		SilanFrame frame = new SilanFrame(ctx);
		ctx.setRootFrame(frame);
		
		SwingUtils.showFrame(frame);
	}
}