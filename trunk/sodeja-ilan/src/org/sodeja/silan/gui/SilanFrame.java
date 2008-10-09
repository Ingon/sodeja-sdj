package org.sodeja.silan.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import org.sodeja.swing.action.LocalizedAction;
import org.sodeja.swing.frame.ApplicationFrame;

public class SilanFrame extends ApplicationFrame<SilanContext> {
	private static final long serialVersionUID = -782666787379121965L;
	
	public SilanFrame(SilanContext ctx) {
		super(ctx);
		setTitle("Silan");
		
		initComponents();
		
		setBounds(100, 100, 200, 200);
	}

	private void initComponents() {
		setLayout(new GridLayout(2, 3));
		
		LocalizedAction<SilanContext> act = new LocalizedAction<SilanContext>(SilanConstants.CLASS_BROWSER) {
			private static final long serialVersionUID = -7770052178250983979L;

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Invoke class browser!");
			}};
		add(new JButton(act));
		
	}
}
