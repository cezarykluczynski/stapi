package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.comic_series.WikitextToComicSeriesProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor;
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.publishable.processor.PublishableTemplatePublishedDatesEnrichingProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ComicsTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, ComicsTemplate>> {

	private final ComicsTemplatePartStaffEnrichingProcessor comicsTemplatePartStaffEnrichingProcessor;

	private final WikitextToCompaniesProcessor wikitextToCompaniesProcessor;

	private final WikitextToComicSeriesProcessor wikitextToComicSeriesProcessor;

	private final WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private final WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor;

	private final PublishableTemplatePublishedDatesEnrichingProcessor publishableTemplatePublishedDatesEnrichingProcessor;

	private final ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor;

	@Inject
	public ComicsTemplatePartsEnrichingProcessor(ComicsTemplatePartStaffEnrichingProcessor comicsTemplatePartStaffEnrichingProcessor,
			WikitextToCompaniesProcessor wikitextToCompaniesProcessor, WikitextToComicSeriesProcessor wikitextToComicSeriesProcessor,
			WikitextToYearRangeProcessor wikitextToYearRangeProcessor, WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor,
			PublishableTemplatePublishedDatesEnrichingProcessor publishableTemplatePublishedDatesEnrichingProcessor,
			ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor) {
		this.comicsTemplatePartStaffEnrichingProcessor = comicsTemplatePartStaffEnrichingProcessor;
		this.wikitextToCompaniesProcessor = wikitextToCompaniesProcessor;
		this.wikitextToComicSeriesProcessor = wikitextToComicSeriesProcessor;
		this.wikitextToYearRangeProcessor = wikitextToYearRangeProcessor;
		this.wikitextToStardateRangeProcessor = wikitextToStardateRangeProcessor;
		this.publishableTemplatePublishedDatesEnrichingProcessor = publishableTemplatePublishedDatesEnrichingProcessor;
		this.referencesFromTemplatePartProcessor = referencesFromTemplatePartProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, ComicsTemplate> enrichablePair) throws Exception {
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case ComicsTemplateParameter.WRITER:
				case ComicsTemplateParameter.ARTIST:
				case ComicsTemplateParameter.EDITOR:
					comicsTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(part, comicsTemplate));
					break;
				case ComicsTemplateParameter.PUBLISHER:
					comicsTemplate.getPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case ComicsTemplateParameter.SERIES:
					comicsTemplate.getComicSeries().addAll(wikitextToComicSeriesProcessor.process(value));
					break;
				case ComicsTemplateParameter.PUBLISHED:
				case ComicsTemplateParameter.COVER_DATE:
					publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(part, comicsTemplate));
					break;
				case ComicsTemplateParameter.PAGES:
					// TODO NumberOfPartsProcessor
					comicsTemplate.setNumberOfPages(Ints.tryParse(value));
					break;
				case ComicsTemplateParameter.YEAR:
					if (comicsTemplate.getYearFrom() == null && comicsTemplate.getYearTo() == null) {
						YearRange yearRange = wikitextToYearRangeProcessor.process(value);
						if (yearRange != null) {
							comicsTemplate.setYearFrom(yearRange.getYearFrom());
							comicsTemplate.setYearTo(yearRange.getYearTo());
						}
					}
					break;
				case ComicsTemplateParameter.STARDATE:
					if (comicsTemplate.getStardateFrom() == null && comicsTemplate.getStardateTo() == null) {
						StardateRange stardateRange = wikitextToStardateRangeProcessor.process(value);
						if (stardateRange != null) {
							comicsTemplate.setStardateFrom(stardateRange.getStardateFrom());
							comicsTemplate.setStardateTo(stardateRange.getStardateTo());
						}
					}
					break;
				case ComicsTemplateParameter.REFERENCE:
					comicsTemplate.getReferences().addAll(referencesFromTemplatePartProcessor.process(part));
					break;
				default:
					break;
			}
		}
	}

}
