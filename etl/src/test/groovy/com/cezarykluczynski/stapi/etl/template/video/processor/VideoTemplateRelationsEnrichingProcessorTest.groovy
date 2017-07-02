package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class VideoTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String REFERENCE = 'REFERENCE'

	private ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessorMock

	private VideoTemplateRelationsEnrichingProcessor videoTemplateRelationsEnrichingProcessor

	void setup() {
		referencesFromTemplatePartProcessorMock = Mock()
		videoTemplateRelationsEnrichingProcessor = new VideoTemplateRelationsEnrichingProcessor(referencesFromTemplatePartProcessorMock)
	}

	void "when reference part is found, ReferencesFromTemplatePartProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoTemplateParameter.REFERENCE,
				value: REFERENCE)
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Reference reference1 = Mock()
		Reference reference2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * referencesFromTemplatePartProcessorMock.process(templatePart) >> Sets.newHashSet(reference1, reference2)
		0 * _
		videoTemplate.references.contains reference1
		videoTemplate.references.contains reference2
	}

}
