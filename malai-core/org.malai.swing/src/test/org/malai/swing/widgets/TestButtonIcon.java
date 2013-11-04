package org.malai.swing.widgets;

import java.awt.Color;
import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.malai.swing.widget.MButtonIcon;

public class TestButtonIcon {
	private MButtonIcon	bi;

	private final Color	cNull	= null;

	private final Color	c1		= new Color(100, 100, 100);

	private final Color	c2		= new Color(50, 50, 50);

	@Before
	public void setUp() {
		bi = new MButtonIcon(c1);
	}


	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		try {
			new MButtonIcon(cNull);
			fail();
		}catch(final IllegalArgumentException e) { /* Good */ }
	}


	@Test
	public void testPaintIcon() {
		final JButton b = new JButton();

		b.setIcon(bi);
		b.repaint();
	}


	@Test
	public void testGetSetColor() {
		bi.setColor(c2);
		assertEquals(c2, bi.getColor());
		bi.setColor(cNull);
		assertEquals(c2, bi.getColor());
	}


	@Test
	public void testGetIconWidthHeight() {
		assertEquals(bi.getIconWidth(), MButtonIcon.WIDTH);
		assertEquals(bi.getIconHeight(), MButtonIcon.HEIGHT);
	}
}
