package com.convertor;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public class DrawIcon implements Icon {
	private int width;
	private int height;

	@Override
	public int getIconHeight() {

		return this.height;
	}

	@Override
	public int getIconWidth() {

		return this.width;
	}

	@Override
	public void paintIcon(Component arg0, Graphics arg1, int arg2, int arg3) {

		arg1.fillOval(arg2, arg3, width, height);

	}

	public DrawIcon(int width, int height) {
		this.width = width;
		this.height = height;
	}

}
