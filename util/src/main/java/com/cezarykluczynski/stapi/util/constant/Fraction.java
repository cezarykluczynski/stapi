package com.cezarykluczynski.stapi.util.constant;

import com.google.common.collect.Maps;

import java.util.Map;

public class Fraction {

	public static final Map<String, Double> MAPPING = Maps.newHashMap();

	static {
		MAPPING.put("⅛", .125d);
		MAPPING.put("¼", .25d);
		MAPPING.put("⅜", .375d);
		MAPPING.put("½", .5d);
		MAPPING.put("⅝", .625d);
		MAPPING.put("¾", .75d);
		MAPPING.put("⅞", .875d);
	}

}
