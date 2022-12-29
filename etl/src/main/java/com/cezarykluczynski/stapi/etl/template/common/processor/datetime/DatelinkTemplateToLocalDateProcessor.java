package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class DatelinkTemplateToLocalDateProcessor implements ItemProcessor<Template, LocalDate> {

	private final DatelinkTemplateToDayMonthYearCandidateProcessor datelinkTemplateToDayMonthYearCandidateProcessor;

	private final DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	public DatelinkTemplateToLocalDateProcessor(DatelinkTemplateToDayMonthYearCandidateProcessor datelinkTemplateToDayMonthYearCandidateProcessor,
												DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor) {
		this.datelinkTemplateToDayMonthYearCandidateProcessor = datelinkTemplateToDayMonthYearCandidateProcessor;
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
	}

	@Override
	public LocalDate process(Template item) throws Exception {
		return dayMonthYearCandidateToLocalDateProcessor.process(datelinkTemplateToDayMonthYearCandidateProcessor.process(item));
	}

}
