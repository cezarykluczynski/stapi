package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.google.common.primitives.Ints;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class NumberOfPartsProcessor implements ItemProcessor<String, Integer> {

	private static final String PLUS_SIGN = "+";

	@Override
	public Integer process(String item) throws Exception {
		if (item == null) {
			return null;
		}

		String valueToParse = item.endsWith(PLUS_SIGN) ? StringUtils.chop(item) : item;
		valueToParse = StringUtils.remove(valueToParse, ",");

		return Ints.tryParse(valueToParse);
	}

}
