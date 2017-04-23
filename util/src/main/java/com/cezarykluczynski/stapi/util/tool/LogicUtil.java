package com.cezarykluczynski.stapi.util.tool;

import org.apache.commons.lang3.RandomUtils;

public class LogicUtil {

	public static boolean xorNull(Object left, Object right) {
		return left != null ^ right != null;
	}

	public static boolean nextBoolean() {
		return RandomUtils.nextInt(0, 2) == 0;
	}

}
