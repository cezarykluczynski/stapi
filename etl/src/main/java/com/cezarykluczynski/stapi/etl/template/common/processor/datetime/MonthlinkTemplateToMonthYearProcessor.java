package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
public class MonthlinkTemplateToMonthYearProcessor implements ItemProcessor<Template, DayMonthYear> {

	private final MonthlinkTemplateToMonthYearCandiateProcessor monthlinkTemplateToMonthYearCandiateProcessor;

	private final MonthYearCandidateToYearMonthProcessor monthYearCandidateToYearMonthProcessor;

	public MonthlinkTemplateToMonthYearProcessor(MonthlinkTemplateToMonthYearCandiateProcessor monthlinkTemplateToMonthYearCandiateProcessor,
			MonthYearCandidateToYearMonthProcessor monthYearCandidateToYearMonthProcessor) {
		this.monthlinkTemplateToMonthYearCandiateProcessor = monthlinkTemplateToMonthYearCandiateProcessor;
		this.monthYearCandidateToYearMonthProcessor = monthYearCandidateToYearMonthProcessor;
	}

	@Override
	public DayMonthYear process(Template item) throws Exception {
		MonthYearCandidate monthYearCandidate = monthlinkTemplateToMonthYearCandiateProcessor.process(item);
		if (monthYearCandidate == null) {
			return null;
		}
		YearMonth localDate = monthYearCandidateToYearMonthProcessor.process(monthYearCandidate);
		if (localDate == null) {
			return null;
		}

		DayMonthYear dayMonthYear = new DayMonthYear();
		dayMonthYear.setMonth(localDate.getMonthValue());
		dayMonthYear.setYear(localDate.getYear());
		return dayMonthYear;
	}

}
