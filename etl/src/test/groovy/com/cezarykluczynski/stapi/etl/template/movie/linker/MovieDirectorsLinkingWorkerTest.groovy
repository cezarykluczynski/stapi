package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieDirectorsLinkingWorkerTest extends Specification {

	private AllStaffFindingMovieRealPeopleLinkingWorkerHelper allStaffFindingMovieRealPeopleLinkingWorkerHelper

	private MovieDirectorsLinkingWorker movieDirectorsLinkingWorker

	void setup() {
		allStaffFindingMovieRealPeopleLinkingWorkerHelper = Mock()
		movieDirectorsLinkingWorker = new MovieDirectorsLinkingWorker(allStaffFindingMovieRealPeopleLinkingWorkerHelper)
	}

	void "adds directors found by AllStaffFindingMovieRealPeopleLinkingWorkerHelperTest"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		Staff director = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieDirectorsLinkingWorker.link(source, baseEntity)

		then:
		1 * allStaffFindingMovieRealPeopleLinkingWorkerHelper.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(director)
		0 * _
		baseEntity.directors.size() == 1
		baseEntity.directors.contains director
	}

}
