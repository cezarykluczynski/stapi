package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityRefreshingLookupByNameService
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class FirstPerformerFindingMovieRealPeopleLinkingWorkerHelperTest extends Specification {

	private static final String PERFORMER = 'PERFORMER'
	private static final String IGNORED = 'IGNORED'
	private static final String PERFORMER_NOT_FOUND = 'PERFORMER_NOT_FOUND'
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private EntityRefreshingLookupByNameService entityRefreshingLookupByNameServiceMock

	private FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper firstPerformerFindingMovieRealPeopleLinkingWorkerHelper

	void setup() {
		entityRefreshingLookupByNameServiceMock = Mock()
		firstPerformerFindingMovieRealPeopleLinkingWorkerHelper = new FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper(
				entityRefreshingLookupByNameServiceMock)
	}

	void "adds entities found by name"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add(Lists.newArrayList())
		source.add(Lists.newArrayList(PERFORMER, IGNORED))
		source.add(Lists.newArrayList(PERFORMER_NOT_FOUND))
		Performer performer = new Performer()

		when:
		Set<Performer> performerSet = firstPerformerFindingMovieRealPeopleLinkingWorkerHelper.linkListsToPerformers(source, SOURCE)

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMER, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(performer)
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMER_NOT_FOUND, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		performerSet.size() == 1
		performerSet.contains performer
	}

}
