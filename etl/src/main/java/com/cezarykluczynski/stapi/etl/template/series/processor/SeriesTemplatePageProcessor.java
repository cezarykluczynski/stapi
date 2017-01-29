package com.cezarykluczynski.stapi.etl.template.series.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToDateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PartToYearRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class SeriesTemplatePageProcessor implements ItemProcessor<Page, SeriesTemplate> {

	private static final String ABBR = "abbr";
	private static final String DATES = "dates";
	private static final String RUN = "run";
	private static final String STUDIO = "studio";
	private static final String NETWORK = "network";

	private PartToYearRangeProcessor partToYearRangeProcessor;

	private PartToDateRangeProcessor partToDateRangeProcessor;

	private PageBindingService pageBindingService;

	private TemplateFinder templateFinder;

	private SeriesTemplateCompanyProcessor seriesTemplateCompanyProcessor;

	@Inject
	public SeriesTemplatePageProcessor(PartToYearRangeProcessor partToYearRangeProcessor,
			PartToDateRangeProcessor partToDateRangeProcessor, PageBindingService pageBindingService,
			TemplateFinder templateFinder, SeriesTemplateCompanyProcessor seriesTemplateCompanyProcessor) {
		this.partToYearRangeProcessor = partToYearRangeProcessor;
		this.partToDateRangeProcessor = partToDateRangeProcessor;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.seriesTemplateCompanyProcessor = seriesTemplateCompanyProcessor;
	}

	@Override
	public SeriesTemplate process(Page item) throws Exception {
		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateName.SIDEBAR_SERIES);

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
				case ABBR:
					seriesTemplate.setAbbreviation(value);
					break;
				case DATES:
					seriesTemplate.setProductionYearRange(partToYearRangeProcessor.process(part));
					break;
				case RUN:
					seriesTemplate.setOriginalRunDateRange(partToDateRangeProcessor.process(part));
					break;
				case STUDIO:
					seriesTemplate.setProductionCompany(seriesTemplateCompanyProcessor.process(part));
					break;
				case NETWORK:
					seriesTemplate.setOriginalBroadcaster(seriesTemplateCompanyProcessor.process(part));
					break;
				default:
					break;
			}
		}

		return seriesTemplate;
	}


}
