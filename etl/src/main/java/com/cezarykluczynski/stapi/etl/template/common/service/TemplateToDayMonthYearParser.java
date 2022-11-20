package com.cezarykluczynski.stapi.etl.template.common.service;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToDayMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.MonthlinkTemplateToMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.YearlinkToYearProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class TemplateToDayMonthYearParser {

	private final DatelinkTemplateToDayMonthYearProcessor datelinkTemplateToDayMonthYearProcessor;

	private final MonthlinkTemplateToMonthYearProcessor monthlinkTemplateToMonthYearProcessor;

	private final YearlinkToYearProcessor yearlinkToYearProcessor;

	public TemplateToDayMonthYearParser(DatelinkTemplateToDayMonthYearProcessor datelinkTemplateToDayMonthYearProcessor,
			MonthlinkTemplateToMonthYearProcessor monthlinkTemplateToMonthYearProcessor,
			YearlinkToYearProcessor yearlinkToYearProcessor) {
		this.datelinkTemplateToDayMonthYearProcessor = datelinkTemplateToDayMonthYearProcessor;
		this.monthlinkTemplateToMonthYearProcessor = monthlinkTemplateToMonthYearProcessor;
		this.yearlinkToYearProcessor = yearlinkToYearProcessor;
	}

	@SneakyThrows
	public DayMonthYear parseDayMonthYearCandidate(Template template) {
		return datelinkTemplateToDayMonthYearProcessor.process(template);
	}

	@SneakyThrows
	public DayMonthYear parseMonthYearCandidate(Template template) {
		return monthlinkTemplateToMonthYearProcessor.process(template);
	}

	@SneakyThrows
	public DayMonthYear parseYearCandidate(Template template) {
		Integer year = yearlinkToYearProcessor.process(template);

		if (year == null) {
			return null;
		}

		DayMonthYear dayMonthYear = new DayMonthYear();
		dayMonthYear.setYear(year);
		return dayMonthYear;
	}

}
