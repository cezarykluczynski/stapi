package com.cezarykluczynski.stapi.etl.season.creation.processor;

import org.springframework.stereotype.Service;

@Service
public class SeasonSeriesAbbreviationFixer {

	public String fix(String abbreviation) {
		if ("SA".equals(abbreviation)) {
			return "SFA";
		}
		return abbreviation;
	}

}
