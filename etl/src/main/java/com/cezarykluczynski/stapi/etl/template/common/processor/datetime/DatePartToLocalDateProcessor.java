package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DatePartToLocalDateProcessor implements ItemProcessor<Template.Part, LocalDate> {

	private final DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessor;

	private final DayMonthYearToLocalDateProcessor dayMonthYearToLocalDateProcessor;

	public DatePartToLocalDateProcessor(DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessor,
			DayMonthYearToLocalDateProcessor dayMonthYearToLocalDateProcessor) {
		this.datePartToDayMonthYearProcessor = datePartToDayMonthYearProcessor;
		this.dayMonthYearToLocalDateProcessor = dayMonthYearToLocalDateProcessor;
	}

	@Override
	public LocalDate process(Template.Part item) throws Exception {
		return dayMonthYearToLocalDateProcessor.process(datePartToDayMonthYearProcessor.process(item));
	}

}
