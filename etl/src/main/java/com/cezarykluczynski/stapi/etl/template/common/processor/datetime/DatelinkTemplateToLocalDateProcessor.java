package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class DatelinkTemplateToLocalDateProcessor implements ItemProcessor<Template, LocalDate> {

	private final DatelinkTemplateToDayMonthYearCandiateProcessor datelinkTemplateToDayMonthYearCandiateProcessor;

	private final DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	public DatelinkTemplateToLocalDateProcessor(DatelinkTemplateToDayMonthYearCandiateProcessor datelinkTemplateToDayMonthYearCandiateProcessor,
			DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor) {
		this.datelinkTemplateToDayMonthYearCandiateProcessor = datelinkTemplateToDayMonthYearCandiateProcessor;
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
	}

	@Override
	public LocalDate process(Template item) throws Exception {
		return dayMonthYearCandidateToLocalDateProcessor.process(datelinkTemplateToDayMonthYearCandiateProcessor.process(item));
	}

}
