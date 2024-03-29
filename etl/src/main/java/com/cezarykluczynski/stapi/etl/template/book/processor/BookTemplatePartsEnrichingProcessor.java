package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartListEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor;
import com.cezarykluczynski.stapi.etl.template.audio.dto.AudioTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.RunTimeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookTemplatePartsEnrichingProcessor implements ItemWithTemplatePartListEnrichingProcessor<BookTemplate> {

	private static final String YES = "yes";

	private final BookTemplatePartStaffEnrichingProcessor bookTemplatePartStaffEnrichingProcessor;

	private final BookTemplatePublishedDatesEnrichingProcessor bookTemplatePublishedDatesEnrichingProcessor;

	private final WikitextToYearRangeProcessor wikitextToYearRangeProcessor;

	private final WikitextToStardateRangeProcessor wikitextToStardateRangeProcessor;

	private final ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor;

	private final RunTimeProcessor runTimeProcessor;

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final BookProductionNumberProcessor bookProductionNumberProcessor;

	private final BookNumberOfPagesProcessor bookNumberOfPagesProcessor;

	@Override
	@SuppressWarnings("CyclomaticComplexity")
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
					bookTemplate.getPublishers().addAll(wikitextToEntitiesProcessor.findCompanies(value));
					break;
				case BookTemplateParameter.AUDIOBOOK_PUBLISHER:
					bookTemplate.getAudiobookPublishers().addAll(wikitextToEntitiesProcessor.findCompanies(value));
					break;
				case BookTemplateParameter.PUBLISHED:
				case BookTemplateParameter.AUDIOBOOK_PUBLISHED:
					bookTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(part, bookTemplate));
					break;
				case BookTemplateParameter.PAGES:
					bookTemplate.setNumberOfPages(bookNumberOfPagesProcessor.process(value));
					break;
				case BookTemplateParameter.YEAR:
					YearRange yearRange = wikitextToYearRangeProcessor.process(value);
					if (yearRange != null) {
						bookTemplate.setYearFrom(yearRange.getYearFrom());
						bookTemplate.setYearTo(yearRange.getYearTo());
					}
					break;
				case BookTemplateParameter.STARDATE:
					StardateRange stardateRange = wikitextToStardateRangeProcessor.process(value);
					if (stardateRange != null) {
						bookTemplate.setStardateFrom(stardateRange.getStardateFrom());
						bookTemplate.setStardateTo(stardateRange.getStardateTo());
					}
					break;
				case BookTemplateParameter.SERIES:
					bookTemplate.getBookSeries().addAll(wikitextToEntitiesProcessor.findBookSeries(value));
					break;
				case BookTemplateParameter.ISBN:
					bookTemplate.getReferences().addAll(referencesFromTemplatePartProcessor.process(part));
					break;
				case BookTemplateParameter.AUDIOBOOK_ISBN:
					bookTemplate.getAudiobookReferences().addAll(referencesFromTemplatePartProcessor.process(part));
					break;
				case BookTemplateParameter.AUDIOBOOK_RUN_TIME:
				case AudioTemplateParameter.TIME:
					bookTemplate.setAudiobookRunTime(runTimeProcessor.process(value));
					break;
				case BookTemplateParameter.AUDIOBOOK_ABRIDGED:
					bookTemplate.setAudiobookAbridged(StringUtils.equalsIgnoreCase(YES, value));
					break;
				case BookTemplateParameter.PRODUCTION:
					bookTemplate.setProductionNumber(bookProductionNumberProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}
