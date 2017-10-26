package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DatelinkTemplateToDayMonthYearProcessor implements ItemProcessor<Template, DayMonthYear> {

	private final DatelinkTemplateToDayMonthYearCandiateProcessor datelinkTemplateToDayMonthYearCandiateProcessor;

	private final DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	public DatelinkTemplateToDayMonthYearProcessor(DatelinkTemplateToDayMonthYearCandiateProcessor datelinkTemplateToDayMonthYearCandiateProcessor,
			DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor) {
		this.datelinkTemplateToDayMonthYearCandiateProcessor = datelinkTemplateToDayMonthYearCandiateProcessor;
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
	}

	@Override
	public DayMonthYear process(Template item) throws Exception {
		DayMonthYearCandidate dayMonthYearCandidate = datelinkTemplateToDayMonthYearCandiateProcessor.process(item);
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
