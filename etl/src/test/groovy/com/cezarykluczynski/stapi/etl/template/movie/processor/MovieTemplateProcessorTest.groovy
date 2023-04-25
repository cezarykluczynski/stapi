package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.AbstractTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template

class MovieTemplateProcessorTest extends AbstractTemplateProcessorTest {

	private MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessorMock

	private MovieTemplateProcessor movieTemplateProcessor

	void setup() {
		movieTemplateStaffEnrichingProcessorMock = Mock()
		movieTemplateProcessor = new MovieTemplateProcessor(movieTemplateStaffEnrichingProcessorMock)
	}

	void "creates stub and enriches with staff"() {
		given:
		Template template = new Template()

		when:
		MovieTemplate movieTemplate = movieTemplateProcessor.process(template)

		then:
		1 * movieTemplateStaffEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Template, ImageTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output instanceof MovieTemplate
		}
		0 * _
		movieTemplate.movieStub instanceof Movie
	}

}
