package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PageToLifeRangeProcessor implements ItemProcessor<Page, DateRange> {

	private final DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	public PageToLifeRangeProcessor(DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor) {
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
	}

	@Override
	public DateRange process(Page item) throws Exception {
		List<Template> bornTemplates = item.getTemplates().stream()
				.filter(template -> TemplateTitle.BORN.equals(template.getTitle()))
				.collect(Collectors.toList());

		if (bornTemplates.isEmpty()) {
			return null;
		}

		if (bornTemplates.size() > 1) {
			log.info("More than one {} template found for \"{}\", using the first one", TemplateTitle.BORN, item.getTitle());
		}

		Template bornTemplate = bornTemplates.get(0);

		String bornDay = null;
		String bornMonth = null;
		String bornYear = null;
		String diedDay = null;
		String diedMonth = null;
		String diedYear = null;

		for (Template.Part part : bornTemplate.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key.equals(TemplateParam.FIRST)) {
				bornDay = value;
			} else if (key.equals(TemplateParam.SECOND)) {
				bornMonth = value;
			} else if (key.equals(TemplateParam.THIRD)) {
				bornYear = value;
			} else if (key.equals(TemplateParam.FIFTH)) {
				diedDay = value;
			} else if (key.equals(TemplateParam.SIXTH)) {
				diedMonth = value;
			} else if (key.equals(TemplateParam.SEVENTH)) {
				diedYear = value;
			}
		}

		LocalDate dateOfBirth = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(bornDay, bornMonth, bornYear));
		LocalDate dateOfDeath = dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(diedDay, diedMonth, diedYear));

		if (dateOfBirth == null && dateOfDeath == null) {
			return null;
		}

		DateRange dateRange = new DateRange();
		dateRange.setStartDate(dateOfBirth);
		dateRange.setEndDate(dateOfDeath);
		return dateRange;
	}

}
