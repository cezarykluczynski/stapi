package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String PUBLISHER = 'PUBLISHER'
	private static final String PUBLISHED = 'PUBLISHED'
	private static final Integer YEAR = 1995
	private static final String ISSUES_STRING = '12'
	private static final Integer ISSUES_INTEGER = 12

	private ComicSeriesTemplatePublishersProcessor comicSeriesTemplatePublishersProcessorMock

	private ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessorMock

	private ComicSeriesTemplateNumberOfIssuesProcessor comicSeriesTemplateNumberOfIssuesProcessorMock

	private ComicSeriesTemplatePartsEnrichingProcessor comicSeriesTemplatePartsEnrichingProcessor

	void setup() {
		comicSeriesTemplatePublishersProcessorMock = Mock(ComicSeriesTemplatePublishersProcessor)
		comicSeriesPublishedDatesEnrichingProcessorMock = Mock(ComicSeriesPublishedDatesEnrichingProcessor)
		comicSeriesTemplateNumberOfIssuesProcessorMock = Mock(ComicSeriesTemplateNumberOfIssuesProcessor)
		comicSeriesTemplatePartsEnrichingProcessor = new ComicSeriesTemplatePartsEnrichingProcessor(comicSeriesTemplatePublishersProcessorMock,
				comicSeriesPublishedDatesEnrichingProcessorMock, comicSeriesTemplateNumberOfIssuesProcessorMock)
	}

	void "sets publishers from ComicSeriesTemplatePublishersProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.PUBLISHER, value: PUBLISHER)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()
		Set publisherSet = Mock(Set)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * comicSeriesTemplatePublishersProcessorMock.process(PUBLISHER) >> publisherSet
		0 * _
		comicSeriesTemplate.publishers == publisherSet
	}

	void "passes ComicSeriesTemplate to ComicSeriesPublishedDatesEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.PUBLISHED, value: PUBLISHED)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * comicSeriesPublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input == templatePart
			assert enrichablePair.output != null
		}
		0 * _
	}

	void "does not pass ComicSeriesTemplate to ComicSeriesPublishedDatesEnrichingProcessor when is already have published years"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.PUBLISHED, value: PUBLISHED)
		ComicSeriesTemplate comicSeriesTemplateWithPublishedYearFrom = new ComicSeriesTemplate(publishedYearFrom: YEAR)
		ComicSeriesTemplate comicSeriesTemplateWithPublishedYearTo = new ComicSeriesTemplate(publishedYearTo: YEAR)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart),
				comicSeriesTemplateWithPublishedYearFrom))

		then:
		0 * _

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart),
				comicSeriesTemplateWithPublishedYearTo))

		then:
		0 * _
	}

	void "sets number of issues from ComicSeriesTemplateNumberOfIssuesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.ISSUES, value: ISSUES_STRING)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * comicSeriesTemplateNumberOfIssuesProcessorMock.process(ISSUES_STRING) >> ISSUES_INTEGER
		0 * _
		comicSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

	void "does not set number of issues from ComicSeriesTemplateNumberOfIssuesProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.ISSUES, value: ISSUES_STRING)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(numberOfIssues: ISSUES_INTEGER)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		0 * _
		comicSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

}
