package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.comicSeries.WikitextToComicSeriesProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ComicsTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, ComicsTemplate>> {

	private static final String PUBLISHED = "published";
	private static final String PUBLISHER = "publisher";
	private static final String YEAR = "year";
	private static final String STARDATE = "stardate";
	private static final String SERIES = "series";
	private static final String WRITER = "writer";
	private static final String ARTIST = "artist";
	private static final String EDITOR = "editor";
	private static final String COVER_DATE = "coverdate";
	private static final String PAGES = "pages";

	private ComicsTemplatePartStaffEnrichingProcessor comicsTemplatePartStaffEnrichingProcessor;

	private WikitextToCompaniesProcessor wikitextToCompaniesProcessor;

	private WikitextToComicSeriesProcessor wikitextToComicSeriesProcessor;

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor;

	private ComicsTemplatePublishedDatesEnrichingProcessor comicsTemplatePublishedDatesEnrichingProcessor;

	@Inject
	public ComicsTemplatePartsEnrichingProcessor(ComicsTemplatePartStaffEnrichingProcessor comicsTemplatePartStaffEnrichingProcessor,
			WikitextToCompaniesProcessor wikitextToCompaniesProcessor, WikitextToComicSeriesProcessor wikitextToComicSeriesProcessor,
			WikitextToYearRangeProcessor wikitextToYearRangeProcessor, WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor,
			ComicsTemplatePublishedDatesEnrichingProcessor comicsTemplatePublishedDatesEnrichingProcessor) {
		this.comicsTemplatePartStaffEnrichingProcessor = comicsTemplatePartStaffEnrichingProcessor;
		this.wikitextToCompaniesProcessor = wikitextToCompaniesProcessor;
		this.wikitextToComicSeriesProcessor = wikitextToComicSeriesProcessor;
		this.wikitextToYearRangeProcessor = wikitextToYearRangeProcessor;
		this.wikitextToStardateRangeProcessor = wikitextToStardateRangeProcessor;
		this.comicsTemplatePublishedDatesEnrichingProcessor = comicsTemplatePublishedDatesEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, ComicsTemplate> enrichablePair) throws Exception {
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case WRITER:
				case ARTIST:
				case EDITOR:
					comicsTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(part, comicsTemplate));
					break;
				case PUBLISHER:
					comicsTemplate.getPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case SERIES:
					comicsTemplate.getComicSeries().addAll(wikitextToComicSeriesProcessor.process(value));
					break;
				case PUBLISHED:
				case COVER_DATE:
					comicsTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(part, comicsTemplate));
					break;
				case PAGES:
					comicsTemplate.setNumberOfPages(Ints.tryParse(value));
					break;
				case YEAR:
					if (comicsTemplate.getYearFrom() == null && comicsTemplate.getYearTo() == null) {
						YearRange yearRange = wikitextToYearRangeProcessor.process(value);
						if (yearRange != null) {
							comicsTemplate.setYearFrom(yearRange.getYearFrom());
							comicsTemplate.setYearTo(yearRange.getYearTo());
						}
					}
					break;
				case STARDATE:
					if (comicsTemplate.getStardateFrom() == null && comicsTemplate.getStardateTo() == null) {
						StardateRange stardateRange = wikitextToStardateRangeProcessor.process(value);
						if (stardateRange != null) {
							comicsTemplate.setStardateFrom(stardateRange.getStardateFrom());
							comicsTemplate.setStardateTo(stardateRange.getStardateTo());
						}
					}
					break;
				default:
					break;
			}
		}
	}

}
