package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToLocalDateProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.Month

class VideoTemplateDatesEnrichingProcessorTest extends Specification {

	private DatePartToLocalDateProcessor datePartToLocalDateProcessorMock

	private VideoTemplateDatesEnrichingProcessor videoTemplateDatesEnrichingProcessor

	void setup() {
		datePartToLocalDateProcessorMock = Mock()
		videoTemplateDatesEnrichingProcessor = new VideoTemplateDatesEnrichingProcessor(datePartToLocalDateProcessorMock)
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		Template.Part templatePart = new Template.Part(key: key)
		LocalDate localDate = LocalDate.of(2001, Month.FEBRUARY, 3)
		VideoTemplate videoTemplate = new VideoTemplate()
		Template template = new Template(parts: Lists.newArrayList(templatePart))
		datePartToLocalDateProcessorMock.process(templatePart) >> localDate

		expect:
		videoTemplateDatesEnrichingProcessor.enrich(EnrichablePair.of(template, videoTemplate))
		videoTemplate[flagName] == localDate

		where:
		key                           | flagName
		VideoTemplateParameter.DATE0  | 'regionFreeReleaseDate'
		VideoTemplateParameter.DATE   | 'region1AReleaseDate'
		VideoTemplateParameter.DATE1S | 'region1SlimlineReleaseDate'
		VideoTemplateParameter.DATE2  | 'region2BReleaseDate'
		VideoTemplateParameter.DATE2S | 'region2SlimlineReleaseDate'
		VideoTemplateParameter.DATE4  | 'region4AReleaseDate'
		VideoTemplateParameter.DATE4S | 'region4SlimlineReleaseDate'
	}

}
