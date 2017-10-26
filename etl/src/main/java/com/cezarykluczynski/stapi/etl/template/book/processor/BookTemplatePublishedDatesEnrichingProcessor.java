package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToDayMonthYearProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

@Service
public class BookTemplatePublishedDatesEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<BookTemplate> {

	private final DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessor;

	private final BookPublishedDateFixedValueProvider bookPublishedDateFixedValueProvider;

	public BookTemplatePublishedDatesEnrichingProcessor(DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessor,
			BookPublishedDateFixedValueProvider bookPublishedDateFixedValueProvider) {
		this.datePartToDayMonthYearProcessor = datePartToDayMonthYearProcessor;
		this.bookPublishedDateFixedValueProvider = bookPublishedDateFixedValueProvider;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, BookTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		BookTemplate bookTemplate = enrichablePair.getOutput();
		String templatePartKey = templatePart.getKey();

		FixedValueHolder<DayMonthYear> publishedDateFixedValueHolder = bookPublishedDateFixedValueProvider.getSearchedValue(bookTemplate.getTitle());

		if (publishedDateFixedValueHolder.isFound()) {
			DayMonthYear publishedDate = publishedDateFixedValueHolder.getValue();
			bookTemplate.setPublishedDay(publishedDate.getDay());
			bookTemplate.setPublishedMonth(publishedDate.getMonth());
			bookTemplate.setPublishedYear(publishedDate.getYear());
			return;
		}

		DayMonthYear dayMonthYear = datePartToDayMonthYearProcessor.process(templatePart);

		if (dayMonthYear == null) {
			return;
		}

		if (BookTemplateParameter.PUBLISHED.equals(templatePartKey)) {
			bookTemplate.setPublishedYear(dayMonthYear.getYear());
			bookTemplate.setPublishedMonth(dayMonthYear.getMonth());
			bookTemplate.setPublishedDay(dayMonthYear.getDay());
		} else if (BookTemplateParameter.AUDIOBOOK_PUBLISHED.equals(templatePartKey)) {
			bookTemplate.setAudiobookPublishedYear(dayMonthYear.getYear());
			bookTemplate.setAudiobookPublishedMonth(dayMonthYear.getMonth());
			bookTemplate.setAudiobookPublishedDay(dayMonthYear.getDay());
		}
	}

}
