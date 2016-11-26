package com.convertor;

import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class MyJDialog extends JDialog {
	public MyJDialog(Convertor frame) {
		
		super(frame, "first window", true);
		Container container = getContentPane(); //
		container.add(new JLabel("this is a dialog"));
		setBounds(120, 120, 100, 100); 
	}
}
