package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieProducersLinkingWorkerTest extends Specification {

	private SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelperMock

	private MovieProducersLinkingWorker movieProducersLinkingWorker

	def setup() {
		simpleMovieRealPeopleLinkingWorkerHelperMock = Mock(SimpleMovieRealPeopleLinkingWorkerHelper)
		movieProducersLinkingWorker = new MovieProducersLinkingWorker(simpleMovieRealPeopleLinkingWorkerHelperMock)
	}

	def "adds producers found by SimpleMovieRealPeopleLinkingWorkerHelper"() {
		given:
		LinkedHashSet<List<String>> source = Sets.newHashSet()
		Staff producer = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieProducersLinkingWorker.link(source, baseEntity)

		then:
		1 * simpleMovieRealPeopleLinkingWorkerHelperMock.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(producer)
		0 * _
		baseEntity.producers.size() == 1
		baseEntity.producers.contains producer
	}

}
