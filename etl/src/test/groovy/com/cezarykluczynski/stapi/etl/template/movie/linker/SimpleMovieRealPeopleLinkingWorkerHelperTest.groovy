package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class SimpleMovieRealPeopleLinkingWorkerHelperTest extends Specification {

	private static final String DIRECTOR = 'DIRECTOR'
	private static final String DIRECTOR_NOT_FOUND = 'DIRECTOR_NOT_FOUND'
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private EntityLookupByNameService entityLookupByNameServiceMock

	private SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelper

	def setup() {
		entityLookupByNameServiceMock = Mock(EntityLookupByNameService)
		simpleMovieRealPeopleLinkingWorkerHelper = new SimpleMovieRealPeopleLinkingWorkerHelper(entityLookupByNameServiceMock)
	}

	def "adds entities found by name"() {
		given:
		LinkedHashSet<List<String>> source = Sets.newLinkedHashSet()
		source.add(Lists.newArrayList(DIRECTOR))
		source.add(Lists.newArrayList(DIRECTOR_NOT_FOUND))
		Staff director = new Staff()

		when:
		Set<Staff> staffSet = simpleMovieRealPeopleLinkingWorkerHelper.linkListsToStaff(source, SOURCE)

		then:
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR_NOT_FOUND, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		staffSet.size() == 1
		staffSet.contains director
	}

}
