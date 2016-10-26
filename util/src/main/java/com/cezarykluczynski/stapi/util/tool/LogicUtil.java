package com.cezarykluczynski.stapi.util.tool;

import org.apache.commons.lang3.RandomUtils;

public class LogicUtil {

	// TODO: remove if this gets released into Maven Central: https://github.com/apache/commons-lang/pull/197
	public static boolean xorNull(Object left, Object right) {
		return left != null ^ right != null;
	}

	public static boolean nextBoolean() {
		return RandomUtils.nextInt(0, 2) == 0;
	}

}
