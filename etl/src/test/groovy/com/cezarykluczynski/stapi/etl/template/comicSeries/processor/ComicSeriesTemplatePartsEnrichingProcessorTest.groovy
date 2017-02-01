package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String PUBLISHER = 'PUBLISHER'
	private static final String PUBLISHED = 'PUBLISHED'

	private ComicSeriesTemplatePublishersProcessor comicSeriesTemplatePublishersProcessorMock

	private ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessorMock

	private ComicSeriesTemplatePartsEnrichingProcessor comicSeriesTemplatePartsEnrichingProcessor

	void setup() {
		comicSeriesTemplatePublishersProcessorMock = Mock(ComicSeriesTemplatePublishersProcessor)
		comicSeriesPublishedDatesEnrichingProcessorMock = Mock(ComicSeriesPublishedDatesEnrichingProcessor)
		comicSeriesTemplatePartsEnrichingProcessor = new ComicSeriesTemplatePartsEnrichingProcessor(comicSeriesTemplatePublishersProcessorMock,
				comicSeriesPublishedDatesEnrichingProcessorMock)
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

}
