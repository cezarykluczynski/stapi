package com.cezarykluczynski.stapi.etl.template.book_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplatePartsEnrichingProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class BookSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String NOVELS_STRING = '12'
	private static final Integer NOVELS_INTEGER = 12
	private static final Integer NOVELS_INTEGER_2 = 22

	private PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessorMock

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private BookSeriesTemplatePartsEnrichingProcessor bookSeriesTemplatePartsEnrichingProcessor

	void setup() {
		publishableSeriesTemplatePartsEnrichingProcessorMock = Mock()
		numberOfPartsProcessorMock = Mock()
		bookSeriesTemplatePartsEnrichingProcessor = new BookSeriesTemplatePartsEnrichingProcessor(
				publishableSeriesTemplatePartsEnrichingProcessorMock, numberOfPartsProcessorMock)
	}

	void "passes enrichable pair to PublishableSeriesTemplatePartsEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.PUBLISHER)
		List<Template.Part> templatePartList = Lists.newArrayList(templatePart)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(templatePartList, bookSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<Template.Part>, PublishableSeriesTemplate> enrichablePair ->
			assert enrichablePair.input == templatePartList
			assert enrichablePair.output == bookSeriesTemplate
		}
		0 * _
	}

	void "parses number of issues"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.NOVELS, value: NOVELS_STRING)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * numberOfPartsProcessorMock.process(NOVELS_STRING) >> NOVELS_INTEGER
		0 * _
		bookSeriesTemplate.numberOfBooks == NOVELS_INTEGER
	}

	void "does not set number of issues, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.NOVELS, value: NOVELS_STRING)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate(numberOfBooks: NOVELS_INTEGER_2)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		0 * _
		bookSeriesTemplate.numberOfBooks == NOVELS_INTEGER_2
	}

}
