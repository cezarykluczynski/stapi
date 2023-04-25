package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DatelinkTemplateToDayMonthYearProcessor implements ItemProcessor<Template, DayMonthYear> {

	private final DatelinkTemplateToDayMonthYearCandidateProcessor datelinkTemplateToDayMonthYearCandidateProcessor;

	private final DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	public DatelinkTemplateToDayMonthYearProcessor(DatelinkTemplateToDayMonthYearCandidateProcessor datelinkTemplateToDayMonthYearCandidateProcessor,
			DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor) {
		this.datelinkTemplateToDayMonthYearCandidateProcessor = datelinkTemplateToDayMonthYearCandidateProcessor;
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
	}

	@Override
	public DayMonthYear process(Template item) throws Exception {
		DayMonthYearCandidate dayMonthYearCandidate = datelinkTemplateToDayMonthYearCandidateProcessor.process(item);
		if (dayMonthYearCandidate == null) {
			return null;
		}
		LocalDate localDate = dayMonthYearCandidateToLocalDateProcessor.process(dayMonthYearCandidate);
		if (localDate == null) {
			return null;
		}

		DayMonthYear dayMonthYear = new DayMonthYear();
		dayMonthYear.setDay(localDate.getDayOfMonth());
		dayMonthYear.setMonth(localDate.getMonthValue());
		dayMonthYear.setYear(localDate.getYear());
		return dayMonthYear;
	}

}
