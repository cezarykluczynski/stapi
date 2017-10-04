package com.cezarykluczynski.stapi.etl.template.comic_strip.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplate
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplateParameter
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.DayMonthYearRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicStripTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String PERIODICAL_DIRTY = '1971 \'\'TV 21 Annual\'\''
	private static final String PERIODICAL_CLEAN = '1971 TV 21 Annual'
	private static final String SERIES = 'SERIES'
	private static final String PUBLISHED = 'PUBLISHED'
	private static final String PAGES_STRING = '32'
	private static final Integer PAGES_INTEGER = 32
	private static final String YEARS = '1995-1997'
	private static final Integer YEAR_FROM = 1995
	private static final Integer YEAR_TO = 1997

	private ComicStripTemplatePartStaffEnrichingProcessor comicStripTemplatePartStaffEnrichingProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private DayMonthYearRangeProcessor dayMonthYearRangeProcessorMock

	private ComicStripTemplateDayMonthYearRangeEnrichingProcessor comicStripTemplateDayMonthYearRangeEnrichingProcessorMock

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessorMock

	private ComicStripTemplatePartsEnrichingProcessor comicStripTemplatePartsEnrichingProcessor

	void setup() {
		comicStripTemplatePartStaffEnrichingProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		dayMonthYearRangeProcessorMock = Mock()
		comicStripTemplateDayMonthYearRangeEnrichingProcessorMock = Mock()
		wikitextToYearRangeProcessorMock = Mock()
		comicStripTemplatePartsEnrichingProcessor = new ComicStripTemplatePartsEnrichingProcessor(comicStripTemplatePartStaffEnrichingProcessorMock,
				wikitextToEntitiesProcessorMock, dayMonthYearRangeProcessorMock, comicStripTemplateDayMonthYearRangeEnrichingProcessorMock,
				wikitextToYearRangeProcessorMock)
	}

	void "passes template part and ComicStripTemplate to ComicStripTemplatePartStaffEnrichingProcessor"() {
		given:
		Template.Part writerTemplatePart = new Template.Part(key: ComicStripTemplateParameter.WRITER)
		Template.Part artistTemplatePart = new Template.Part(key: ComicStripTemplateParameter.ARTIST)
		List<Template.Part> templatePartList = Lists.newArrayList(writerTemplatePart, artistTemplatePart)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()

		when:
		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(templatePartList, comicStripTemplate))

		then:
		1 * comicStripTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template.Part, ComicStripTemplate> enrichablePair ->
			assert enrichablePair.input == writerTemplatePart
			assert enrichablePair.output == comicStripTemplate
		}
		1 * comicStripTemplatePartStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, ComicStripTemplate> enrichablePair ->
				assert enrichablePair.input == artistTemplatePart
				assert enrichablePair.output == comicStripTemplate
		}
		0 * _
	}

	void "sets periodical, and cleans it of single quotation marks"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicStripTemplateParameter.PERIODICAL, value: PERIODICAL_DIRTY)
		List<Template.Part> templatePartList = Lists.newArrayList(templatePart)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()

		when:
		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(templatePartList, comicStripTemplate))

		then:
		comicStripTemplate.periodical == PERIODICAL_CLEAN
		0 * _
	}

	void "gets all comic series from WikitextToEntitiesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicStripTemplateParameter.SERIES, value: SERIES)
		ComicSeries comicSeries1 = Mock()
		ComicSeries comicSeries2 = Mock()
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()

		when:
		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicStripTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findComicSeries(SERIES) >> Lists.newArrayList(comicSeries1, comicSeries2)
		0 * _
		comicStripTemplate.comicSeries.size() == 2
		comicStripTemplate.comicSeries.contains comicSeries1
		comicStripTemplate.comicSeries.contains comicSeries2
	}

	void "does not pass DayMonthYear range to enricher if it is empty"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicStripTemplateParameter.PUBLISHED, value: PUBLISHED)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()

		when:
		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicStripTemplate))

		then:
		1 * dayMonthYearRangeProcessorMock.process(templatePart) >> Range.of(null, null)
		0 * _
	}

	void "passes DayMonthYear range to enricher if it is not empty"() {
		given:
		DayMonthYear dayMonthYearFrom = Mock()
		DayMonthYear dayMonthYearTo = Mock()
		Template.Part templatePart = new Template.Part(key: ComicStripTemplateParameter.PUBLISHED, value: PUBLISHED)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()
		Range range = Range.of(dayMonthYearFrom, dayMonthYearTo)

		when:
		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicStripTemplate))

		then:
		1 * dayMonthYearRangeProcessorMock.process(templatePart) >> range
		1 * comicStripTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Range<DayMonthYear>, ComicStripTemplate> enrichablePair ->
			assert enrichablePair.input == range
			assert enrichablePair.output == comicStripTemplate
		}
		0 * _
	}

	void "sets number of pages"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.PAGES, value: PAGES_STRING)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()

		when:
		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicStripTemplate))

		then:
		0 * _
		comicStripTemplate.numberOfPages == PAGES_INTEGER
	}

	void "sets year from and year to from WikitextToYearRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.YEAR, value: YEARS)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()
		YearRange yearRange = new YearRange(yearFrom: YEAR_FROM, yearTo: YEAR_TO)

		when:
		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicStripTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> yearRange
		0 * _
		comicStripTemplate.yearFrom == YEAR_FROM
		comicStripTemplate.yearTo == YEAR_TO
	}

	void "does not set set year from and year to from WikitextToYearRangeProcessor when YearRange is null"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.YEAR, value: YEARS)
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()

		when:
		comicStripTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicStripTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> null
		0 * _
		comicStripTemplate.yearFrom == null
		comicStripTemplate.yearTo == null
	}

}
