package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieStandInPerformersLinkingWorkerTest extends Specification {

	private FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper firstPerformerFindingMovieRealPeopleLinkingWorkerHelperMock

	private MovieStandInPerformersLinkingWorker movieStandInPerformersLinkingWorker

	void setup() {
		firstPerformerFindingMovieRealPeopleLinkingWorkerHelperMock = Mock()
		movieStandInPerformersLinkingWorker = new MovieStandInPerformersLinkingWorker(
				firstPerformerFindingMovieRealPeopleLinkingWorkerHelperMock)
	}

	void "adds stand in performers found by FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		Performer standInPerformer = new Performer()
		Movie baseEntity = new Movie()

		when:
		movieStandInPerformersLinkingWorker.link(source, baseEntity)

		then:
		1 * firstPerformerFindingMovieRealPeopleLinkingWorkerHelperMock
				.linkListsToPerformers(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(standInPerformer)
		0 * _
		baseEntity.standInPerformers.size() == 1
		baseEntity.standInPerformers.contains standInPerformer
	}

}
