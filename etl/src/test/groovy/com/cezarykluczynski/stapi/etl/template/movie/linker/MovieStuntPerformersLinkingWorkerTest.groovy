package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieStuntPerformersLinkingWorkerTest extends Specification {

	private FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper firstPerformerFindingMovieRealPeopleLinkingWorkerHelperMock

	private MovieStuntPerformersLinkingWorker movieStuntPerformersLinkingWorker

	void setup() {
		firstPerformerFindingMovieRealPeopleLinkingWorkerHelperMock = Mock()
		movieStuntPerformersLinkingWorker = new MovieStuntPerformersLinkingWorker(
				firstPerformerFindingMovieRealPeopleLinkingWorkerHelperMock)
	}

	void "adds stunt performers found by FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		Performer stuntPerformer = new Performer()
		Movie baseEntity = new Movie()

		when:
		movieStuntPerformersLinkingWorker.link(source, baseEntity)

		then:
		1 * firstPerformerFindingMovieRealPeopleLinkingWorkerHelperMock
				.linkListsToPerformers(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(stuntPerformer)
		0 * _
		baseEntity.stuntPerformers.size() == 1
		baseEntity.stuntPerformers.contains stuntPerformer
	}

}
