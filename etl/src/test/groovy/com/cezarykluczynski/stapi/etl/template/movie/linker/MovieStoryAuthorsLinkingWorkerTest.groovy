package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieStoryAuthorsLinkingWorkerTest extends Specification {

	private SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelperMock

	private MovieStoryAuthorsLinkingWorker movieStoryAuthorsLinkingWorker

	def setup() {
		simpleMovieRealPeopleLinkingWorkerHelperMock = Mock(SimpleMovieRealPeopleLinkingWorkerHelper)
		movieStoryAuthorsLinkingWorker = new MovieStoryAuthorsLinkingWorker(simpleMovieRealPeopleLinkingWorkerHelperMock)
	}

	def "adds storyAuthors found by SimpleMovieRealPeopleLinkingWorkerHelper"() {
		given:
		LinkedHashSet<List<String>> source = Sets.newHashSet()
		Staff storyAuthor = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieStoryAuthorsLinkingWorker.link(source, baseEntity)

		then:
		1 * simpleMovieRealPeopleLinkingWorkerHelperMock.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(storyAuthor)
		0 * _
		baseEntity.storyAuthors.size() == 1
		baseEntity.storyAuthors.contains storyAuthor
	}

}
