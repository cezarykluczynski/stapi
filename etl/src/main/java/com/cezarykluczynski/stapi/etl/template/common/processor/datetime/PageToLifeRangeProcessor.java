package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class PageToLifeRangeProcessor implements ItemProcessor<Page, DateRange> {

	@Override
	public DateRange process(Page item) throws Exception {
		// TODO
		return null;
	}

}
