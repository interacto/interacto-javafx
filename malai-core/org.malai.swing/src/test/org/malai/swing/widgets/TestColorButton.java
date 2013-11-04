package org.malai.swing.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import org.malai.swing.widget.MButtonIcon;
import org.malai.swing.widget.MColorButton;

public class TestColorButton {
	private MColorButton cb1;

	private MColorButton cb2;

	private final Color	c = new Color(100, 100, 100);

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
