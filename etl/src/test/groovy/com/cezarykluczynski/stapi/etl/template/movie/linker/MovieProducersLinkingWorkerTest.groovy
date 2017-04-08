package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieProducersLinkingWorkerTest extends Specification {

	private AllStaffFindingMovieRealPeopleLinkingWorkerHelper allStaffFindingMovieRealPeopleLinkingWorkerHelper

	private MovieProducersLinkingWorker movieProducersLinkingWorker

	void setup() {
		allStaffFindingMovieRealPeopleLinkingWorkerHelper = Mock()
		movieProducersLinkingWorker = new MovieProducersLinkingWorker(allStaffFindingMovieRealPeopleLinkingWorkerHelper)
	}

	void "adds producers found by AllStaffFindingMovieRealPeopleLinkingWorkerHelperTest"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		Staff producer = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieProducersLinkingWorker.link(source, baseEntity)

		then:
		1 * allStaffFindingMovieRealPeopleLinkingWorkerHelper.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(producer)
		0 * _
		baseEntity.producers.size() == 1
		baseEntity.producers.contains producer
	}

}
