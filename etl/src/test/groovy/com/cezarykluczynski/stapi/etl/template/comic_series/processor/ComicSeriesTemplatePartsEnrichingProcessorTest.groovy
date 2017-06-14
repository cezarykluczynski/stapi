package com.cezarykluczynski.stapi.etl.template.comic_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplatePartsEnrichingProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String ISSUES_STRING = '12'
	private static final Integer ISSUES_INTEGER = 12
	private static final Integer ISSUES_INTEGER_2 = 22

	private PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessorMock

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private ComicSeriesTemplatePartsEnrichingProcessor comicSeriesTemplatePartsEnrichingProcessor

	void setup() {
		publishableSeriesTemplatePartsEnrichingProcessorMock = Mock()
		numberOfPartsProcessorMock = Mock()
		comicSeriesTemplatePartsEnrichingProcessor = new ComicSeriesTemplatePartsEnrichingProcessor(
				publishableSeriesTemplatePartsEnrichingProcessorMock, numberOfPartsProcessorMock)
	}

	void "passes enrichable pair to PublishableSeriesTemplatePartsEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplateParameter.PUBLISHER)
		List<Template.Part> templatePartList = Lists.newArrayList(templatePart)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(templatePartList, comicSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<Template.Part>, PublishableSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePartList
				assert enrichablePair.output == comicSeriesTemplate
		}
		0 * _
	}

	void "parses number of issues"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplateParameter.ISSUES, value: ISSUES_STRING)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * numberOfPartsProcessorMock.process(ISSUES_STRING) >> ISSUES_INTEGER
		0 * _
		comicSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

	void "does not set number of issues, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplateParameter.ISSUES, value: ISSUES_STRING)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(numberOfIssues: ISSUES_INTEGER_2)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		0 * _
		comicSeriesTemplate.numberOfIssues == ISSUES_INTEGER_2
	}

}
