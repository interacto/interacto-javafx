package org.malai.swing.widgets;

import java.awt.Color;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.malai.swing.widget.MButtonIcon;
import org.malai.swing.widget.MColorButton;

public class TestColorButton extends TestCase {
	private MColorButton cb1;

	private MColorButton cb2;

	private final Color	c = new Color(100, 100, 100);


	@Override
	@Before
	public void setUp() {
		MButtonIcon bi = new MButtonIcon(c);

		cb1 = new MColorButton("coucou");
		cb2 = new MColorButton(bi);
		assertEquals(c, cb2.getColor());
	}


	@Test
	public void testGetSetColor() {
		cb2.setColor(c);
		assertEquals(c, cb2.getColor());
		assertNull(cb1.getColor());

		cb1.setColor(c);
		assertNull(cb1.getColor());
	}

}
