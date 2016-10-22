package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToLocalDateProcessor;
import com.cezarykluczynski.stapi.util.constant.TemplateNames;
import com.cezarykluczynski.stapi.wiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ActorTemplateToLifeRangeProcessor extends AbstractTemplateProcessor
		implements ItemProcessor<Template, DateRange> {

	private static final String KEY_DATE_OF_BIRTH = "date of birth";
	private static final String KEY_DATE_OF_DEATH = "date of death";

	private DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessor;

	@Inject
	public ActorTemplateToLifeRangeProcessor(DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessor) {
		this.datelinkTemplateToLocalDateProcessor = datelinkTemplateToLocalDateProcessor;
	}

	@Override
	public DateRange process(Template item) throws Exception {
		if (!TemplateNames.SIDEBAR_ACTOR.equals(item.getTitle())) {
				log.warn("Template {} passed to TemplateToLifeRangeProcessor::process was of different type", item);
				return null;
		}

		LocalDate dateOfBirth = null;
		LocalDate dateOfDeath = null;

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();

			if (KEY_DATE_OF_BIRTH.equals(key)) {
				dateOfBirth = tryExtractDate(part.getTemplates());
			} else if (KEY_DATE_OF_DEATH.equals(key)) {
				dateOfDeath = tryExtractDate(part.getTemplates());

			}
		}

		if (dateOfBirth != null || dateOfDeath != null) {
			DateRange dateRange = new DateRange();
			dateRange.setStartDate(dateOfBirth);
			dateRange.setEndDate(dateOfDeath);
			return dateRange;
		}

		return null;
	}

	private LocalDate tryExtractDate(List<Template> templateList) throws Exception {
		if (CollectionUtils.isEmpty(templateList)) {
			return null;
		}

		List<Template> dateTemplateList = filterByTitle(templateList, TemplateNames.D, TemplateNames.DATELINK);

		if (dateTemplateList.isEmpty()) {
			return null;
		}

		return datelinkTemplateToLocalDateProcessor.process(dateTemplateList.get(0));
	}

}
