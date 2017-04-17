package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BookTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, BookTemplate>> {

	private final BookTemplatePartStaffEnrichingProcessor bookTemplatePartStaffEnrichingProcessor;

	private final WikitextToCompaniesProcessor wikitextToCompaniesProcessor;

	private final BookTemplatePublishedDatesEnrichingProcessor bookTemplatePublishedDatesEnrichingProcessor;

	@Inject
	public BookTemplatePartsEnrichingProcessor(BookTemplatePartStaffEnrichingProcessor bookTemplatePartStaffEnrichingProcessor,
			WikitextToCompaniesProcessor wikitextToCompaniesProcessor,
			BookTemplatePublishedDatesEnrichingProcessor bookTemplatePublishedDatesEnrichingProcessor) {
		this.bookTemplatePartStaffEnrichingProcessor = bookTemplatePartStaffEnrichingProcessor;
		this.wikitextToCompaniesProcessor = wikitextToCompaniesProcessor;
		this.bookTemplatePublishedDatesEnrichingProcessor = bookTemplatePublishedDatesEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, BookTemplate> enrichablePair) throws Exception {
		BookTemplate bookTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case BookTemplateParameter.AUTHOR:
				case BookTemplateParameter.ARTIST:
				case BookTemplateParameter.EDITOR:
				case BookTemplateParameter.AUDIOBOOK_READ_BY:
					bookTemplatePartStaffEnrichingProcessor.enrich(EnrichablePair.of(part, bookTemplate));
					break;
				case BookTemplateParameter.PUBLISHER:
					bookTemplate.getPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case BookTemplateParameter.AUDIOBOOK_PUBLISHER:
					bookTemplate.getAudiobookPublishers().addAll(wikitextToCompaniesProcessor.process(value));
					break;
				case BookTemplateParameter.PUBLISHED:
				case BookTemplateParameter.AUDIOBOOK_PUBLISHED:
					bookTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(part, bookTemplate));
					break;
				case BookTemplateParameter.SERIES:
				case BookTemplateParameter.PAGES:
				case BookTemplateParameter.YEAR:
				case BookTemplateParameter.STARDATE:
				case BookTemplateParameter.ISBN:
				case BookTemplateParameter.AUDIOBOOK:
				case BookTemplateParameter.AUDIOBOOK_ABRIDGED:
				case BookTemplateParameter.AUDIOBOOK_RUN_TIME:
				case BookTemplateParameter.AUDIOBOOK_ISBN:
				case BookTemplateParameter.PRODUCTION:
					// TODO
					break;
				default:
					break;
			}
		}
	}

}
