package com.cezarykluczynski.stapi.etl.template.series.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToDateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToYearRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.series.service.SeriesPageFilter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeriesTemplatePageProcessor implements ItemProcessor<Page, SeriesTemplate> {

	private final SeriesPageFilter seriesPageFilter;

	private final PartToYearRangeProcessor partToYearRangeProcessor;

	private final PartToDateRangeProcessor partToDateRangeProcessor;

	private final PageBindingService pageBindingService;

	private final TemplateFinder templateFinder;

	private final SeriesTemplateCompanyProcessor seriesTemplateCompanyProcessor;

	public SeriesTemplatePageProcessor(SeriesPageFilter seriesPageFilter, PartToYearRangeProcessor partToYearRangeProcessor,
			PartToDateRangeProcessor partToDateRangeProcessor, PageBindingService pageBindingService, TemplateFinder templateFinder,
			SeriesTemplateCompanyProcessor seriesTemplateCompanyProcessor) {
		this.seriesPageFilter = seriesPageFilter;
		this.partToYearRangeProcessor = partToYearRangeProcessor;
		this.partToDateRangeProcessor = partToDateRangeProcessor;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.seriesTemplateCompanyProcessor = seriesTemplateCompanyProcessor;
	}

	@Override
	public SeriesTemplate process(Page item) throws Exception {
		if (seriesPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_SERIES);

		if (!templateOptional.isPresent()) {
			return null;
		}

		Template template = templateOptional.get();
		SeriesTemplate seriesTemplate = new SeriesTemplate();
		seriesTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		// Title from the template is ambiguous in ENT, so page title is preferred.
		seriesTemplate.setTitle(item.getTitle());

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case SeriesTemplateParameter.ABBR:
					seriesTemplate.setAbbreviation(value);
					break;
				case SeriesTemplateParameter.DATES:
					seriesTemplate.setProductionYearRange(partToYearRangeProcessor.process(part));
					break;
				case SeriesTemplateParameter.RUN:
					seriesTemplate.setOriginalRunDateRange(partToDateRangeProcessor.process(part));
					break;
				case SeriesTemplateParameter.STUDIO:
					seriesTemplate.setProductionCompany(seriesTemplateCompanyProcessor.process(part));
					break;
				case SeriesTemplateParameter.NETWORK:
					seriesTemplate.setOriginalBroadcaster(seriesTemplateCompanyProcessor.process(part));
					break;
				default:
					break;
			}
		}

		return seriesTemplate;
	}

}
