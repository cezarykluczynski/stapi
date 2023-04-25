package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.movie.creation.service.MovieExistingEntitiesHelper
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieStaffLinkingWorkerTest extends Specification {

	private static final String COMPANY = 'COMPANY'
	private static final String STAFF = 'STAFF'
	private static final String STAFF_NOT_FOUND = 'STAFF_NOT_FOUND'

	private EntityLookupByNameService entityLookupByNameServiceMock

	private MovieExistingEntitiesHelper movieExistingEntitiesHelperMock

	private MovieStaffLinkingWorker movieStaffLinkingWorkerMock

	void setup() {
		entityLookupByNameServiceMock = Mock()
		movieExistingEntitiesHelperMock = Mock()
		movieStaffLinkingWorkerMock = new MovieStaffLinkingWorker(entityLookupByNameServiceMock, movieExistingEntitiesHelperMock)
	}

	void "adds entities found by name"() {
		given:
		Movie movie = new Movie()
		Set<List<String>> source = Sets.newHashSet()
		source.add(Lists.newArrayList(COMPANY))
		source.add(Lists.newArrayList(STAFF))
		source.add(Lists.newArrayList(STAFF_NOT_FOUND))
		Staff director = new Staff()

		when:
		movieStaffLinkingWorkerMock.link(source, movie)

		then:
		1 * movieExistingEntitiesHelperMock.isKnownCompany(COMPANY) >> true
		1 * movieExistingEntitiesHelperMock.isKnownCompany(STAFF) >> false
		1 * entityLookupByNameServiceMock.findStaffByName(STAFF, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)
		1 * movieExistingEntitiesHelperMock.isKnownCompany(STAFF_NOT_FOUND) >> false
		1 * entityLookupByNameServiceMock.findStaffByName(STAFF_NOT_FOUND, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		movie.staff.size() == 1
		movie.staff.contains director
	}

}
