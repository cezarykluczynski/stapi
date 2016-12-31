package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieWritersLinkingWorkerTest extends Specification {

	private SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelperMock

	private MovieWritersLinkingWorker movieWritersLinkingWorker

	def setup() {
		simpleMovieRealPeopleLinkingWorkerHelperMock = Mock(SimpleMovieRealPeopleLinkingWorkerHelper)
		movieWritersLinkingWorker = new MovieWritersLinkingWorker(simpleMovieRealPeopleLinkingWorkerHelperMock)
	}

	def "adds writers found by SimpleMovieRealPeopleLinkingWorkerHelper"() {
		given:
		LinkedHashSet<List<String>> source = Sets.newHashSet()
		Staff writer = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieWritersLinkingWorker.link(source, baseEntity)

		then:
		1 * simpleMovieRealPeopleLinkingWorkerHelperMock.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(writer)
		0 * _
		baseEntity.writers.size() == 1
		baseEntity.writers.contains writer
	}

}
