package com.cezarykluczynski.stapi.etl.util.constant;

import com.google.common.collect.Maps;

import java.util.Map;

public class SeriesAbbreviation {

	public static final String TOS = "TOS";
	public static final String TAS = "TAS";
	public static final String TNG = "TNG";
	public static final String DS9 = "DS9";
	public static final String VOY = "VOY";
	public static final String ENT = "ENT";
	public static final String DIS = "DIS";
	public static final String LD = "LD";
	public static final String PIC = "PIC";
	public static final String PRO = "PRO";
	public static final String SNW = "SNW";

	public static final Map<String, String> SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS = Maps.newLinkedHashMap();

	static {
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(TOS, TOS);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put("TOS-R", TOS);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(TAS, TAS);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(TNG, TNG);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(DS9, DS9);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(VOY, VOY);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(ENT, ENT);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(DIS, DIS);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(LD, LD);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(PIC, PIC);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(PRO, PRO);
		SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.put(SNW, SNW);
	}

}
