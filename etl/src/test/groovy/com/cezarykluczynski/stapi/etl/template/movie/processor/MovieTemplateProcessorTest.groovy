package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.AbstractTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template

class MovieTemplateProcessorTest extends AbstractTemplateProcessorTest {

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessorMock

	private MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessorMock

	private MovieTemplateProcessor movieTemplateProcessor

	void setup() {
		imageTemplateStardateYearEnrichingProcessorMock = Mock()
		movieTemplateStaffEnrichingProcessorMock = Mock()
		movieTemplateProcessor = new MovieTemplateProcessor(imageTemplateStardateYearEnrichingProcessorMock,
				movieTemplateStaffEnrichingProcessorMock)
	}

	void "sets values from template parts"() {
		given:
		Template template = new Template()

		when:
		MovieTemplate movieTemplate = movieTemplateProcessor.process(template)

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Template, ImageTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output instanceof MovieTemplate
		}
		1 * movieTemplateStaffEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Template, ImageTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output instanceof MovieTemplate
		}
		0 * _
		movieTemplate.movieStub instanceof Movie
	}

}
