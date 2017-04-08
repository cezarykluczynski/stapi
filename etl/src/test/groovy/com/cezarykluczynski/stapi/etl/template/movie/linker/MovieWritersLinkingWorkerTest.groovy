package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieWritersLinkingWorkerTest extends Specification {

	private AllStaffFindingMovieRealPeopleLinkingWorkerHelper allStaffFindingMovieRealPeopleLinkingWorkerHelper

	private MovieWritersLinkingWorker movieWritersLinkingWorker

	void setup() {
		allStaffFindingMovieRealPeopleLinkingWorkerHelper = Mock()
		movieWritersLinkingWorker = new MovieWritersLinkingWorker(allStaffFindingMovieRealPeopleLinkingWorkerHelper)
	}

	void "adds writers found by AllStaffFindingMovieRealPeopleLinkingWorkerHelperTest"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		Staff writer = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieWritersLinkingWorker.link(source, baseEntity)

		then:
		1 * allStaffFindingMovieRealPeopleLinkingWorkerHelper.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(writer)
		0 * _
		baseEntity.writers.size() == 1
		baseEntity.writers.contains writer
	}

}
