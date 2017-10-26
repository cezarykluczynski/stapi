package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToLocalDateProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ActorTemplateToLifeRangeProcessor implements ItemProcessor<Template, DateRange> {

	private DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessor;

	private TemplateFilter templateFilter;

	public ActorTemplateToLifeRangeProcessor(DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessor,
			TemplateFilter templateFilter) {
		this.datelinkTemplateToLocalDateProcessor = datelinkTemplateToLocalDateProcessor;
		this.templateFilter = templateFilter;
	}

	@Override
	public DateRange process(Template item) throws Exception {
		if (!TemplateTitle.SIDEBAR_ACTOR.equals(item.getTitle())) {
			log.warn("Template {} passed to TemplateToLifeRangeProcessor::process was of different type", item);
			return null;
		}

		LocalDate dateOfBirth = null;
		LocalDate dateOfDeath = null;

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();

			if (ActorTemplateParameter.DATE_OF_BIRTH.equals(key)) {
				dateOfBirth = tryExtractDate(part.getTemplates());
			} else if (ActorTemplateParameter.DATE_OF_DEATH.equals(key)) {
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

		List<Template> dateTemplateList = templateFilter.filterByTitle(templateList, TemplateTitle.D, TemplateTitle.DATELINK);

		if (dateTemplateList.isEmpty()) {
			return null;
		}

		return datelinkTemplateToLocalDateProcessor.process(dateTemplateList.get(0));
	}

}
