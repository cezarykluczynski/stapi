package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieStoryAuthorsLinkingWorkerTest extends Specification {

	private AllStaffFindingMovieRealPeopleLinkingWorkerHelper allStaffFindingMovieRealPeopleLinkingWorkerHelper

	private MovieStoryAuthorsLinkingWorker movieStoryAuthorsLinkingWorker

	void setup() {
		allStaffFindingMovieRealPeopleLinkingWorkerHelper = Mock()
		movieStoryAuthorsLinkingWorker = new MovieStoryAuthorsLinkingWorker(allStaffFindingMovieRealPeopleLinkingWorkerHelper)
	}

	void "adds storyAuthors found by AllStaffFindingMovieRealPeopleLinkingWorkerHelperTest"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		Staff storyAuthor = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieStoryAuthorsLinkingWorker.link(source, baseEntity)

		then:
		1 * allStaffFindingMovieRealPeopleLinkingWorkerHelper.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(storyAuthor)
		0 * _
		baseEntity.storyAuthors.size() == 1
		baseEntity.storyAuthors.contains storyAuthor
	}

}
