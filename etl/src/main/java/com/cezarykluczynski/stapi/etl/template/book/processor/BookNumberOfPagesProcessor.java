package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookNumberOfPagesProcessor implements ItemProcessor<String, Integer> {

	private static final String COMA = ",";

	@Override
	public Integer process(String value) throws Exception {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		String valueToParse = value;
		valueToParse = StringUtil.substringBeforeAll(valueToParse, List.of("+", ", ", " &bull;", "; "," (", "<", " / ", " & "));
		valueToParse = StringUtils.trim(valueToParse);
		if (valueToParse.length() >= 5 && COMA.equals(valueToParse.substring(1, 2))) {
			valueToParse = valueToParse.charAt(0) + valueToParse.substring(2);
		} else if (valueToParse.length() >= 6 && COMA.equals(valueToParse.substring(2, 3))) {
			valueToParse = valueToParse.substring(0, 2) + valueToParse.substring(3);
		}
		Integer numberOfPages = Ints.tryParse(valueToParse);
		if (numberOfPages == null) {
			log.info("Number of pages \"{}\" could not be parsed.", value);
		}
		return numberOfPages;
	}

}
