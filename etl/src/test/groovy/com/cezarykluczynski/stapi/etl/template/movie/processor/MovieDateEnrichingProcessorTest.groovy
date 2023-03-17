package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class MovieDateEnrichingProcessorTest extends Specification {

	static final Float STARDATE_FROM = 12093.5F
	static final Float STARDATE_TO = 12321.4F
	static final Integer YEAR_FROM = 2350
	static final Integer YEAR_TO = 2351

	static final String TITLE = 'TITLE'

	MovieTemplateStardateYearFixedValueProvider movieTemplateStardateYearFixedValueProviderMock

	MovieDateEnrichingProcessor movieDateEnrichingProcessor

	void setup() {
		movieTemplateStardateYearFixedValueProviderMock = Mock()
		movieDateEnrichingProcessor = new MovieDateEnrichingProcessor(movieTemplateStardateYearFixedValueProviderMock)
	}

	void "when fixed value is found, it is used"() {
		given:
		Page page = new Page(title: TITLE)
		MovieTemplate movieTemplate = new MovieTemplate()
		StardateYearDTO stardateYearDTO = new StardateYearDTO(
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO)

		when:
		movieDateEnrichingProcessor.enrich(EnrichablePair.of(page, movieTemplate))

		then:
		1 * movieTemplateStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(stardateYearDTO)
		0 * _
		movieTemplate.stardateFrom == STARDATE_FROM
		movieTemplate.stardateTo == STARDATE_TO
		movieTemplate.yearFrom == YEAR_FROM
		movieTemplate.yearTo == YEAR_TO
	}

	void "when fixes value is not found, nothing happens"() {
		given:
		Page page = new Page(title: TITLE)
		MovieTemplate movieTemplate = new MovieTemplate()

		when:
		movieDateEnrichingProcessor.enrich(EnrichablePair.of(page, movieTemplate))

		then:
		1 * movieTemplateStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		0 * _
		movieTemplate.stardateFrom == null
		movieTemplate.stardateTo == null
		movieTemplate.yearFrom == null
		movieTemplate.yearTo == null
	}

}
