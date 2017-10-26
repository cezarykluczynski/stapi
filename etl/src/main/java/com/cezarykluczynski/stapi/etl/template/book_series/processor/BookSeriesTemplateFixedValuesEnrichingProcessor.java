package com.cezarykluczynski.stapi.etl.template.book_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesTemplateFixedValuesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<BookSeriesTemplate, BookSeriesTemplate>> {

	private final BookSeriesPublishedDateFixedValueProvider bookSeriesPublishedDateFixedValueProvider;

	private final PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;

	private final BookSeriesTemplateNumberOfBooksFixedValueProvider bookSeriesTemplateNumberOfIssuesFixedValueProvider;

	public BookSeriesTemplateFixedValuesEnrichingProcessor(BookSeriesPublishedDateFixedValueProvider bookSeriesPublishedDateFixedValueProvider,
			PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor,
			BookSeriesTemplateNumberOfBooksFixedValueProvider bookSeriesTemplateNumberOfIssuesFixedValueProvider) {
		this.bookSeriesPublishedDateFixedValueProvider = bookSeriesPublishedDateFixedValueProvider;
		this.publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor = publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;
		this.bookSeriesTemplateNumberOfIssuesFixedValueProvider = bookSeriesTemplateNumberOfIssuesFixedValueProvider;
	}

	@Override
	public void enrich(EnrichablePair<BookSeriesTemplate, BookSeriesTemplate> enrichablePair) throws Exception {
		BookSeriesTemplate bookSeriesTemplate = enrichablePair.getOutput();
		String title = bookSeriesTemplate.getTitle();

		FixedValueHolder<Range<DayMonthYear>> dayMonthYearRangeFixedValueHolder = bookSeriesPublishedDateFixedValueProvider.getSearchedValue(title);

		if (dayMonthYearRangeFixedValueHolder.isFound()) {
			Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeFixedValueHolder.getValue();
			publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
					.enrich(EnrichablePair.of(Pair.of(null, dayMonthYearRange), bookSeriesTemplate));
		}

		FixedValueHolder<Integer> numberOfIssuesFixedValueHolder = bookSeriesTemplateNumberOfIssuesFixedValueProvider.getSearchedValue(title);

		if (numberOfIssuesFixedValueHolder.isFound()) {
			bookSeriesTemplate.setNumberOfBooks(numberOfIssuesFixedValueHolder.getValue());
		}
	}

}
