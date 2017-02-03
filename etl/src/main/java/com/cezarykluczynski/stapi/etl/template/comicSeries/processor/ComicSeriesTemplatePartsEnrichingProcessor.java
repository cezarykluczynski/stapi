package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ComicSeriesTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, ComicSeriesTemplate>> {

	private static final String PUBLISHED = "published";
	private static final String PUBLISHER = "publisher";
	private static final String ISSUES = "issues";
	private static final String YEAR = "year";
	private static final String STARDATE = "stardate";
	private static final String SERIES = "series";

	private ComicSeriesTemplatePublishersProcessor comicSeriesTemplatePublishersProcessor;

	private ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessor;

	private ComicSeriesTemplateNumberOfIssuesProcessor comicSeriesTemplateNumberOfIssuesProcessor;

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor;

	private ComicSeriesTemplateMiniseriesProcessor comicSeriesTemplateMiniseriesProcessor;

	@Inject
	public ComicSeriesTemplatePartsEnrichingProcessor(ComicSeriesTemplatePublishersProcessor comicSeriesTemplatePublishersProcessor,
			ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessor,
			ComicSeriesTemplateNumberOfIssuesProcessor comicSeriesTemplateNumberOfIssuesProcessor,
			WikitextToYearRangeProcessor wikitextToYearRangeProcessor,
			WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor,
			ComicSeriesTemplateMiniseriesProcessor comicSeriesTemplateMiniseriesProcessor) {
		this.comicSeriesTemplatePublishersProcessor = comicSeriesTemplatePublishersProcessor;
		this.comicSeriesPublishedDatesEnrichingProcessor = comicSeriesPublishedDatesEnrichingProcessor;
		this.comicSeriesTemplateNumberOfIssuesProcessor = comicSeriesTemplateNumberOfIssuesProcessor;
		this.wikitextToYearRangeProcessor = wikitextToYearRangeProcessor;
		this.wikitextToStardateRangeProcessor = wikitextToStardateRangeProcessor;
		this.comicSeriesTemplateMiniseriesProcessor = comicSeriesTemplateMiniseriesProcessor;
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
				case ISSUES:
					if (comicSeriesTemplate.getNumberOfIssues() == null) {
						comicSeriesTemplate.setNumberOfIssues(comicSeriesTemplateNumberOfIssuesProcessor.process(value));
					}
					break;
				case YEAR:
					if (comicSeriesTemplate.getYearFrom() == null && comicSeriesTemplate.getYearTo() == null) {
						YearRange yearRange = wikitextToYearRangeProcessor.process(value);
						if (yearRange != null) {
							comicSeriesTemplate.setYearFrom(yearRange.getStartYear());
							comicSeriesTemplate.setYearTo(yearRange.getEndYear());
						}
					}
					break;
				case STARDATE:
					if (comicSeriesTemplate.getStardateFrom() == null && comicSeriesTemplate.getStardateTo() == null) {
						StardateRange stardateRange = wikitextToStardateRangeProcessor.process(value);
						if (stardateRange != null) {
							comicSeriesTemplate.setStardateFrom(stardateRange.getStardateFrom());
							comicSeriesTemplate.setStardateTo(stardateRange.getStardateTo());
						}
					}
					break;
				case SERIES:
					if (comicSeriesTemplate.getMiniseries() == null) {
						comicSeriesTemplate.setMiniseries(comicSeriesTemplateMiniseriesProcessor.process(value));
					}
					break;
				default:
					break;
			}
		}
	}
}
