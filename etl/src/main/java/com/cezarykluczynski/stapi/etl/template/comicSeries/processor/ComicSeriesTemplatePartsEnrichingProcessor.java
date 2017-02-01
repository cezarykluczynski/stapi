package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ComicSeriesTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, ComicSeriesTemplate>> {

	private static final String PUBLISHED = "published";
	private static final String PUBLISHER = "publisher";

	private ComicSeriesTemplatePublishersProcessor comicSeriesTemplatePublishersProcessor;

	private ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessor;

	@Inject
	public ComicSeriesTemplatePartsEnrichingProcessor(ComicSeriesTemplatePublishersProcessor comicSeriesTemplatePublishersProcessor,
			ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessor) {
		this.comicSeriesTemplatePublishersProcessor = comicSeriesTemplatePublishersProcessor;
		this.comicSeriesPublishedDatesEnrichingProcessor = comicSeriesPublishedDatesEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, ComicSeriesTemplate> enrichablePair) throws Exception {
		ComicSeriesTemplate comicSeriesTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case PUBLISHER:
					comicSeriesTemplate.setPublishers(comicSeriesTemplatePublishersProcessor.process(value));
					break;
				case PUBLISHED:
					if (comicSeriesTemplate.getPublishedYearFrom() == null && comicSeriesTemplate.getPublishedYearTo() == null) {
						comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(part, comicSeriesTemplate));
					}
					break;
				default:
					break;
			}
		}
	}
}
