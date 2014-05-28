package org.malai.swing.widgets;

import static org.junit.Assert.*;

import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;
import org.malai.swing.widget.MComboBox;

public class TestMComboBox {
	MComboBox<String> combo;
	
	@Before public void setUp() throws Exception {
		combo = new MComboBox<>();
	}

	@Test public void testsetgetLabel() {
		JLabel l1 = new JLabel("l1");
		JLabel l2 = new JLabel("l2");
		combo.setLabel(l1);
		assertEquals(l1, combo.getLabel());
		assertEquals(combo, l1.getLabelFor());
		combo.setLabel(l2);
		assertNull(l1.getLabelFor());
		assertEquals(combo, l2.getLabelFor());
		assertEquals(l2, combo.getLabel());
	}
}
