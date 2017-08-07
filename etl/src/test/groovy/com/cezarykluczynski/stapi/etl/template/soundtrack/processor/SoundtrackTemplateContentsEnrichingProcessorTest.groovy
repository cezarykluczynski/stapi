package com.cezarykluczynski.stapi.etl.template.soundtrack.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.RecordingTimeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToLocalDateProcessor
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class SoundtrackContentsEnrichingProcessorTest extends Specification {

	private static final LocalDate RELEASED_DATE = LocalDate.of(2001, 01, 01)
	private static final String DISKS_STRING = 'DISKS_STRING'
	private static final Integer DISKS_INTEGER = 4
	private static final String LENGTH_STRING = 'LENGTH_STRING'
	private static final Integer LENGTH_INTEGER = 2985

	private DatePartToLocalDateProcessor datePartToLocalDateProcessorMock

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private RecordingTimeProcessor recordingTimeProcessorMock

	private SoundtrackTemplateContentsEnrichingProcessor soundtrackTemplateContentsEnrichingProcessor

	void setup() {
		datePartToLocalDateProcessorMock = Mock()
		numberOfPartsProcessorMock = Mock()
		recordingTimeProcessorMock = Mock()
		soundtrackTemplateContentsEnrichingProcessor = new SoundtrackTemplateContentsEnrichingProcessor(datePartToLocalDateProcessorMock,
				numberOfPartsProcessorMock, recordingTimeProcessorMock)
	}

	void "when released part is found, DatePartToLocalDateProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: SoundtrackTemplateParameter.RELEASED)
		Template sidebarSoundtrackTemplate = new Template(parts: Lists.newArrayList(templatePart))
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()

		when:
		soundtrackTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarSoundtrackTemplate, soundtrackTemplate))

		then:
		1 * datePartToLocalDateProcessorMock.process(templatePart) >> RELEASED_DATE
		0 * _
		soundtrackTemplate.releaseDate == RELEASED_DATE
	}

	void "when disks part is found, NumberOfPartsProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: SoundtrackTemplateParameter.DISKS,
				value: DISKS_STRING)
		Template sidebarSoundtrackTemplate = new Template(parts: Lists.newArrayList(templatePart))
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()

		when:
		soundtrackTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarSoundtrackTemplate, soundtrackTemplate))

		then:
		1 * numberOfPartsProcessorMock.process(DISKS_STRING) >> DISKS_INTEGER
		0 * _
		soundtrackTemplate.numberOfDataCarriers == DISKS_INTEGER
	}

	void "when length part is found, RecordingTimeProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: SoundtrackTemplateParameter.LENGTH,
				value: LENGTH_STRING)
		Template sidebarSoundtrackTemplate = new Template(parts: Lists.newArrayList(templatePart))
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()

		when:
		soundtrackTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarSoundtrackTemplate, soundtrackTemplate))

		then:
		1 * recordingTimeProcessorMock.process(LENGTH_STRING) >> LENGTH_INTEGER
		0 * _
		soundtrackTemplate.length == LENGTH_INTEGER
	}

}
