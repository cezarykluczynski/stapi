package com.cezarykluczynski.stapi.etl.template.series.processor;

import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToDateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToYearRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.util.constants.TemplateNames;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import com.cezarykluczynski.stapi.wiki.dto.Template;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class SeriesTemplatePageProcessor extends AbstractTemplateProcessor implements ItemProcessor<Page, SeriesTemplate> {

	private static final String ABBR = "abbr";
	private static final String DATES = "dates";
	private static final String RUN = "run";

	private PartToYearRangeProcessor partToYearRangeProcessor;

	private PartToDateRangeProcessor partToDateRangeProcessor;

	@Inject
	public SeriesTemplatePageProcessor(PartToYearRangeProcessor partToYearRangeProcessor,
			PartToDateRangeProcessor partToDateRangeProcessor) {
		this.partToYearRangeProcessor = partToYearRangeProcessor;
		this.partToDateRangeProcessor = partToDateRangeProcessor;
	}

	@Override
	public SeriesTemplate process(Page item) throws Exception {
		Optional<Template> templateOptional = findTemplate(item, TemplateNames.SIDEBAR_SERIES);

		if (!templateOptional.isPresent()) {
			return null;
		}

		Template template = templateOptional.get();
		SeriesTemplate seriesTemplate = new SeriesTemplate();

		// Title from the template is ambiguous in ENT, so page title is preferred.
		seriesTemplate.setTitle(item.getTitle());

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();
			switch(key) {
				case ABBR:
					seriesTemplate.setAbbreviation(value);
					break;
				case DATES:
					seriesTemplate.setProductionYearRange(partToYearRangeProcessor.process(part));
					break;
				case RUN:
					seriesTemplate.setOriginalRunDateRange(partToDateRangeProcessor.process(part));
					break;
			}
		}

		return seriesTemplate;
	}


}
