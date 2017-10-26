package com.cezarykluczynski.stapi.etl.template.common.service;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToDayMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.MonthlinkTemplateToMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.YearlinkToYearProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
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

	public DayMonthYear parseDayMonthYearCandidate(Template template) throws Exception {
		return datelinkTemplateToDayMonthYearProcessor.process(template);
	}

	public DayMonthYear parseMonthYearCandidate(Template template) throws Exception {
		return monthlinkTemplateToMonthYearProcessor.process(template);
	}

	public DayMonthYear parseYearCandidate(Template template) throws Exception {
		Integer year = yearlinkToYearProcessor.process(template);

		if (year == null) {
			return null;
		}

		DayMonthYear dayMonthYear = new DayMonthYear();
		dayMonthYear.setYear(year);
		return dayMonthYear;
	}

}
