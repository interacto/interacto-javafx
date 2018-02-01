package org.malai;

import java.awt.GraphicsEnvironment;

public abstract class HelperTest {
	public static boolean isX11Set() {
		return !GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance();
	}
}
