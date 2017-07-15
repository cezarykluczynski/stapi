package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class TextToDayMonthYearProcessor implements ItemProcessor<String, DayMonthYear> {

	@Override
	public DayMonthYear process(String item) throws Exception {
		// TODO
		return null;
	}

}
