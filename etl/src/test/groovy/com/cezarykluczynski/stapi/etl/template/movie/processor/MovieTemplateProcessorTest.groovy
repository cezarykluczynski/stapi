package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.AbstractTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearCandidateToLocalDateProcessor
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplateParameter
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists

import java.time.LocalDate

class MovieTemplateProcessorTest extends AbstractTemplateProcessorTest {

	private static final String RELEASE_DATE_YEAR = '1998'
	private static final String RELEASE_DATE_MONTH = 'April'
	private static final String RELEASE_DATE_DAY = '15'

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessorMock

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessorMock

	private MovieTemplateStaffEnrichingProcessor movieTemplateStaffEnrichingProcessorMock

	private MovieTemplateProcessor movieTemplateProcessor

	void setup() {
		dayMonthYearCandidateToLocalDateProcessorMock = Mock()
		imageTemplateStardateYearEnrichingProcessorMock = Mock()
		movieTemplateStaffEnrichingProcessorMock = Mock()
		movieTemplateProcessor = new MovieTemplateProcessor(dayMonthYearCandidateToLocalDateProcessorMock,
				imageTemplateStardateYearEnrichingProcessorMock, movieTemplateStaffEnrichingProcessorMock)
	}

	void "sets values from template parts"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(MovieTemplateParameter.N_RELEASE_YEAR , RELEASE_DATE_YEAR),
						createTemplatePart(MovieTemplateParameter.S_RELEASE_MONTH, RELEASE_DATE_MONTH),
						createTemplatePart(MovieTemplateParameter.N_RELEASE_DAY, RELEASE_DATE_DAY)
				)
		)
		LocalDate usReleaseDate = LocalDate.of(1998, 4, 15)

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
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == RELEASE_DATE_DAY
			dayMonthYearCandidate.month == RELEASE_DATE_MONTH
			dayMonthYearCandidate.year == RELEASE_DATE_YEAR
			usReleaseDate
		}
		0 * _
		movieTemplate.movieStub instanceof Movie
		movieTemplate.usReleaseDate == usReleaseDate
	}

}
