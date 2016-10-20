package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.util.constants.TemplateNames;
import com.cezarykluczynski.stapi.util.constants.TemplateParam;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import com.cezarykluczynski.stapi.wiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PageToLifeRangeProcessor implements ItemProcessor<Page, DateRange> {

	private DayMonthYearProcessor dayMonthYearProcessor;

	@Inject
	public PageToLifeRangeProcessor(DayMonthYearProcessor dayMonthYearProcessor) {
		this.dayMonthYearProcessor = dayMonthYearProcessor;
	}

	@Override
	public DateRange process(Page item) throws Exception {
		List<Template> bornTemplates = item.getTemplates().stream()
				.filter(template -> TemplateNames.BORN.equals(template.getTitle()))
				.collect(Collectors.toList());

		if (bornTemplates.isEmpty()) {
			return null;
		}

		if (bornTemplates.size() > 1) {
			log.warn("More than one {} template found for {}, using the first one", TemplateNames.BORN, item.getTitle());
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
			}

			if (key.equals(TemplateParam.SECOND)) {
				bornMonth = value;
			}

			if (key.equals(TemplateParam.THIRD)) {
				bornYear = value;
			}

			if (key.equals(TemplateParam.FIFTH)) {
				diedDay = value;
			}

			if (key.equals(TemplateParam.SIXTH)) {
				diedMonth = value;
			}

			if (key.equals(TemplateParam.SEVENTH)) {
				diedYear = value;
			}
		}

		LocalDate dateOfBirth = dayMonthYearProcessor.process(DayMonthYearCandidate.of(bornDay, bornMonth, bornYear));
		LocalDate dateOfDeath = dayMonthYearProcessor.process(DayMonthYearCandidate.of(diedDay, diedMonth, diedYear));

		if (dateOfBirth == null && dateOfDeath == null) {
			return null;
		}

		DateRange dateRange = new DateRange();
		dateRange.setStartDate(dateOfBirth);
		dateRange.setEndDate(dateOfDeath);
		return dateRange;
	}

}
