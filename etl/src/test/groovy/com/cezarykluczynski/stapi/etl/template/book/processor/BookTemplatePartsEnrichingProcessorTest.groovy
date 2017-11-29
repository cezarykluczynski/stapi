package com.cezarykluczynski.stapi.etl.template.book.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.RunTimeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class BookTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String AUTHOR = 'AUTHOR'
	private static final String ARTIST = 'ARTIST'
	private static final String EDITOR = 'EDITOR'
	private static final String AUDIOBOOK_NARRATOR = 'AUDIOBOOK_NARRATOR'
	private static final String PUBLISHER = 'PUBLISHER'
	private static final String AUDIOBOOK_PUBLISHER = 'AUDIOBOOK_PUBLISHER'
	private static final String PUBLISHED = 'PUBLISHED'
	private static final String AUDIOBOOK_PUBLISHED = 'AUDIOBOOK_PUBLISHED'
	private static final String YEARS = '1995-1997'
	private static final Integer YEAR_FROM = 1995
	private static final Integer YEAR_TO = 1997
	private static final String PAGES_STRING = '32'
	private static final Integer PAGES_INTEGER = 32
	private static final String STARDATES = '1995-1997'
	private static final Float STARDATE_FROM = 123.4F
	private static final Float STARDATE_TO = 456.7F
	private static final String SERIES = 'SERIES'
	private static final String REFERENCE = 'REFERENCE'
	private static final String AUDIOBOOK_REFERENCE = 'AUDIOBOOK_REFERENCE'
	private static final String YES = 'YES'
	private static final String PRODUCTION_NUMBER = 'PRODUCTION_NUMBER'
	private static final String AUDIOBOOK_RUN_TIME_STRING = 'AUDIOBOOK_RUN_TIME_STRING'
	private static final Integer AUDIOBOOK_RUN_TIME_INTEGER = 78

	private BookTemplatePartStaffEnrichingProcessor bookTemplatePartStaffEnrichingProcessorMock

	private BookTemplatePublishedDatesEnrichingProcessor bookTemplatePublishedDatesEnrichingProcessorMock

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessorMock

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessorMock

	private ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessorMock

	private RunTimeProcessor runTimeProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private BookTemplatePartsEnrichingProcessor bookTemplatePartsEnrichingProcessor

	void setup() {
		bookTemplatePartStaffEnrichingProcessorMock = Mock()
		bookTemplatePublishedDatesEnrichingProcessorMock = Mock()
		wikitextToYearRangeProcessorMock = Mock()
		wikitextToStardateRangeProcessorMock = Mock()
		referencesFromTemplatePartProcessorMock = Mock()
		runTimeProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		bookTemplatePartsEnrichingProcessor = new BookTemplatePartsEnrichingProcessor(bookTemplatePartStaffEnrichingProcessorMock,
				bookTemplatePublishedDatesEnrichingProcessorMock, wikitextToYearRangeProcessorMock, wikitextToStardateRangeProcessorMock,
				referencesFromTemplatePartProcessorMock, runTimeProcessorMock, wikitextToEntitiesProcessorMock)
	}

	void "passes BookTemplate to BookTemplatePartStaffEnrichingProcessor when author part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUTHOR, value: AUTHOR)
		BookTemplate bookTemplate = new BookTemplate()
		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes BookTemplate to BookTemplatePartStaffEnrichingProcessor when artist part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.ARTIST, value: ARTIST)
		BookTemplate bookTemplate = new BookTemplate()
		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes BookTemplate to BookTemplatePartStaffEnrichingProcessor when editor part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.EDITOR, value: EDITOR)
		BookTemplate bookTemplate = new BookTemplate()
		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes BookTemplate to BookTemplatePartStaffEnrichingProcessor when audiobook read by part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_READ_BY, value: AUDIOBOOK_NARRATOR)
		BookTemplate bookTemplate = new BookTemplate()
		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "sets publishers from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.PUBLISHER, value: PUBLISHER)
		BookTemplate bookTemplate = new BookTemplate()
		Company company1 = Mock()
		Company company2 = Mock()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCompanies(PUBLISHER) >> Lists.newArrayList(company1, company2)
		0 * _
		bookTemplate.publishers.size() == 2
		bookTemplate.publishers.contains company1
		bookTemplate.publishers.contains company2
	}

	void "sets book series from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.SERIES, value: SERIES)
		BookTemplate bookTemplate = new BookTemplate()
		BookSeries bookSeries1 = Mock()
		BookSeries bookSeries2 = Mock()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findBookSeries(SERIES) >> Lists.newArrayList(bookSeries1, bookSeries2)
		0 * _
		bookTemplate.bookSeries.size() == 2
		bookTemplate.bookSeries.contains bookSeries1
		bookTemplate.bookSeries.contains bookSeries2
	}

	void "sets audiobook publishers from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_PUBLISHER, value: AUDIOBOOK_PUBLISHER)
		BookTemplate bookTemplate = new BookTemplate()
		Company company1 = Mock()
		Company company2 = Mock()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCompanies(AUDIOBOOK_PUBLISHER) >> Lists.newArrayList(company1, company2)
		0 * _
		bookTemplate.audiobookPublishers.size() == 2
		bookTemplate.audiobookPublishers.contains company1
		bookTemplate.audiobookPublishers.contains company2
	}

	void "passes BookTemplate to BookTemplatePublishedDatesEnrichingProcessor, when published part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.PUBLISHED, value: PUBLISHED)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "passes BookTemplate to BookTemplatePublishedDatesEnrichingProcessor, when audiobook published part is found"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_PUBLISHED, value: AUDIOBOOK_PUBLISHED)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * bookTemplatePublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "sets number of pages"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.PAGES, value: PAGES_STRING)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		0 * _
		bookTemplate.numberOfPages == PAGES_INTEGER
	}

	void "sets year from and year to from WikitextToYearRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.YEAR, value: YEARS)
		BookTemplate bookTemplate = new BookTemplate()
		YearRange yearRange = new YearRange(yearFrom: YEAR_FROM, yearTo: YEAR_TO)

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> yearRange
		0 * _
		bookTemplate.yearFrom == YEAR_FROM
		bookTemplate.yearTo == YEAR_TO
	}

	void "sets stardate from and stardate to from WikitextToStardateRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.STARDATE, value: STARDATES)
		BookTemplate bookTemplate = new BookTemplate()
		StardateRange stardateRange = StardateRange.of(STARDATE_FROM, STARDATE_TO)

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * wikitextToStardateRangeProcessorMock.process(STARDATES) >> stardateRange
		0 * _
		bookTemplate.stardateFrom == STARDATE_FROM
		bookTemplate.stardateTo == STARDATE_TO
	}

	void "sets references from ReferencesFromTemplatePartProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.ISBN, value: REFERENCE)
		BookTemplate bookTemplate = new BookTemplate()
		Reference reference1 = Mock()
		Reference reference2 = Mock()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * referencesFromTemplatePartProcessorMock.process(templatePart) >> Sets.newHashSet(reference1, reference2)
		0 * _
		bookTemplate.references.size() == 2
		bookTemplate.references.contains reference1
		bookTemplate.references.contains reference2
	}

	void "sets audiobook references from ReferencesFromTemplatePartProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_ISBN, value: AUDIOBOOK_REFERENCE)
		BookTemplate bookTemplate = new BookTemplate()
		Reference reference1 = Mock()
		Reference reference2 = Mock()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * referencesFromTemplatePartProcessorMock.process(templatePart) >> Sets.newHashSet(reference1, reference2)
		0 * _
		bookTemplate.audiobookReferences.size() == 2
		bookTemplate.audiobookReferences.contains reference1
		bookTemplate.audiobookReferences.contains reference2
	}

	void "sets audiobook flag"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK, value: YES)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		0 * _
		bookTemplate.audiobook
	}

	void "sets audiobook abridged flag"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_ABRIDGED, value: YES)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		0 * _
		bookTemplate.audiobookAbridged
	}

	void "sets audiobook run time from RunTimeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_RUN_TIME, value: AUDIOBOOK_RUN_TIME_STRING)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		1 * runTimeProcessorMock.process(AUDIOBOOK_RUN_TIME_STRING) >> AUDIOBOOK_RUN_TIME_INTEGER
		0 * _
		bookTemplate.audiobookRunTime == AUDIOBOOK_RUN_TIME_INTEGER
	}

	void "sets production number"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.PRODUCTION, value: PRODUCTION_NUMBER)
		BookTemplate bookTemplate = new BookTemplate()

		when:
		bookTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookTemplate))

		then:
		0 * _
		bookTemplate.productionNumber == PRODUCTION_NUMBER
	}

}
