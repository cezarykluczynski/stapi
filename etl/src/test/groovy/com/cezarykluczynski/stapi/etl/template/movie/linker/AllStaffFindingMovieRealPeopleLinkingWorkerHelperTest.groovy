package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class AllStaffFindingMovieRealPeopleLinkingWorkerHelperTest extends Specification {

	private static final String DIRECTOR = 'DIRECTOR'
	private static final String DIRECTOR_NOT_FOUND = 'DIRECTOR_NOT_FOUND'
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private EntityLookupByNameService entityLookupByNameServiceMock

	private AllStaffFindingMovieRealPeopleLinkingWorkerHelper allStaffFindingMovieRealPeopleLinkingWorkerHelper

	void setup() {
		entityLookupByNameServiceMock = Mock()
		allStaffFindingMovieRealPeopleLinkingWorkerHelper = new AllStaffFindingMovieRealPeopleLinkingWorkerHelper(entityLookupByNameServiceMock)
	}

	void "adds entities found by name"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add(Lists.newArrayList(DIRECTOR, DIRECTOR_NOT_FOUND))
		Staff director = new Staff()

		when:
		Set<Staff> staffSet = allStaffFindingMovieRealPeopleLinkingWorkerHelper.linkListsToStaff(source, SOURCE)

		then:
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR_NOT_FOUND, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		staffSet.size() == 1
		staffSet.contains director
	}

}
