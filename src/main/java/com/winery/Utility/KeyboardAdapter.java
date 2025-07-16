package com.winery.Utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardAdapter implements KeyListener {

	private Runnable supp;

	public KeyboardAdapter(final Runnable supp) {
		this.supp = supp;
	}

	@Override
	public void keyPressed(final KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_ENTER) {
			supp.run();
		}
	}

	@Override
	public void keyReleased(final KeyEvent k) {

	}

	@Override
	public void keyTyped(final KeyEvent k) {
	}
}