package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookNumberOfPagesProcessor implements ItemProcessor<String, Integer> {

	@Override
	public Integer process(String value) throws Exception {
		if (StringUtils.isNotBlank(value)) {
			Integer numberOfPages = Ints.tryParse(value);
			if (numberOfPages == null) {
				log.info("Number of pages {} could not be parsed.", value);
			}
			return numberOfPages;
		}
		return null;
	}

}
