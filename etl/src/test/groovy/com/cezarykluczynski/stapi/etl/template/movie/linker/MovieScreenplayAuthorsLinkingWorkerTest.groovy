package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieScreenplayAuthorsLinkingWorkerTest extends Specification {

	private AllStaffFindingMovieRealPeopleLinkingWorkerHelper allStaffFindingMovieRealPeopleLinkingWorkerHelper

	private MovieScreenplayAuthorsLinkingWorker movieScreenplayAuthorsLinkingWorker

	void setup() {
		allStaffFindingMovieRealPeopleLinkingWorkerHelper = Mock()
		movieScreenplayAuthorsLinkingWorker = new MovieScreenplayAuthorsLinkingWorker(allStaffFindingMovieRealPeopleLinkingWorkerHelper)
	}

	void "adds screenplayAuthors found by AllStaffFindingMovieRealPeopleLinkingWorkerHelperTest"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		Staff screenplayAuthor = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieScreenplayAuthorsLinkingWorker.link(source, baseEntity)

		then:
		1 * allStaffFindingMovieRealPeopleLinkingWorkerHelper.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(screenplayAuthor)
		0 * _
		baseEntity.screenplayAuthors.size() == 1
		baseEntity.screenplayAuthors.contains screenplayAuthor
	}

}
