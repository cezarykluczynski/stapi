package com.cezarykluczynski.stapi.etl.template.bookSeries.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor;
import com.cezarykluczynski.stapi.etl.template.bookSeries.dto.BookSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.bookSeries.dto.BookSeriesTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PublishableSeriesPublishedDatesEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PublishableSeriesTemplateMiniseriesProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BookSeriesTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, BookSeriesTemplate>> {

	private WikitextToCompaniesProcessor wikitextToCompaniesProcessor;

	private PublishableSeriesPublishedDatesEnrichingProcessor publishableSeriesPublishedDatesEnrichingProcessor;

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor;

	private PublishableSeriesTemplateMiniseriesProcessor publishableSeriesTemplateMiniseriesProcessor;

	@Inject
	public BookSeriesTemplatePartsEnrichingProcessor(WikitextToCompaniesProcessor wikitextToCompaniesProcessor,
			PublishableSeriesPublishedDatesEnrichingProcessor publishableSeriesPublishedDatesEnrichingProcessor,
			WikitextToYearRangeProcessor wikitextToYearRangeProcessor, WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor,
			PublishableSeriesTemplateMiniseriesProcessor publishableSeriesTemplateMiniseriesProcessor) {
		this.wikitextToCompaniesProcessor = wikitextToCompaniesProcessor;
		this.publishableSeriesPublishedDatesEnrichingProcessor = publishableSeriesPublishedDatesEnrichingProcessor;
		this.wikitextToYearRangeProcessor = wikitextToYearRangeProcessor;
		this.wikitextToStardateRangeProcessor = wikitextToStardateRangeProcessor;
		this.publishableSeriesTemplateMiniseriesProcessor = publishableSeriesTemplateMiniseriesProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, BookSeriesTemplate> enrichablePair) throws Exception {
		BookSeriesTemplate bookSeriesTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case BookSeriesTemplateParameter.PUBLISHER:
					bookSeriesTemplate.getPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case BookSeriesTemplateParameter.PUBLISHED:
					if (bookSeriesTemplate.getPublishedYearFrom() == null && bookSeriesTemplate.getPublishedYearTo() == null) {
						publishableSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(part, bookSeriesTemplate));
					}
					break;
				case BookSeriesTemplateParameter.NOVELS:
					if (bookSeriesTemplate.getNumberOfBooks() == null) {
						bookSeriesTemplate.setNumberOfBooks(Ints.tryParse(value));
					}
					break;
				case BookSeriesTemplateParameter.YEAR:
					if (bookSeriesTemplate.getYearFrom() == null && bookSeriesTemplate.getYearTo() == null) {
						YearRange yearRange = wikitextToYearRangeProcessor.process(value);
						if (yearRange != null) {
							bookSeriesTemplate.setYearFrom(yearRange.getYearFrom());
							bookSeriesTemplate.setYearTo(yearRange.getYearTo());
						}
					}
					break;
				case BookSeriesTemplateParameter.STARDATE:
					if (bookSeriesTemplate.getStardateFrom() == null && bookSeriesTemplate.getStardateTo() == null) {
						StardateRange stardateRange = wikitextToStardateRangeProcessor.process(value);
						if (stardateRange != null) {
							bookSeriesTemplate.setStardateFrom(stardateRange.getStardateFrom());
							bookSeriesTemplate.setStardateTo(stardateRange.getStardateTo());
						}
					}
					break;
				case BookSeriesTemplateParameter.SERIES:
					if (bookSeriesTemplate.getMiniseries() == null) {
						bookSeriesTemplate.setMiniseries(publishableSeriesTemplateMiniseriesProcessor.process(value));
					}
					break;
				default:
					break;
			}
		}
	}

}
