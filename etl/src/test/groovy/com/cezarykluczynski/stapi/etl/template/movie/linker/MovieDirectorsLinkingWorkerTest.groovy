package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieDirectorsLinkingWorkerTest extends Specification {

	private static final String DIRECTOR = 'DIRECTOR'
	private static final String DIRECTOR_NOT_FOUND = 'DIRECTOR_NOT_FOUND'

	private EntityLookupByNameService entityLookupByNameServiceMock

	private MovieDirectorsLinkingWorker movieDirectorsLinkingWorker

	def setup() {
		entityLookupByNameServiceMock = Mock(EntityLookupByNameService)
		movieDirectorsLinkingWorker = new MovieDirectorsLinkingWorker(entityLookupByNameServiceMock)
	}

	def "adds directors found by name"() {
		given:
		LinkedHashSet<List<String>> source = Sets.newLinkedHashSet()
		source.add(Lists.newArrayList(DIRECTOR))
		source.add(Lists.newArrayList(DIRECTOR_NOT_FOUND))
		Staff director = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieDirectorsLinkingWorker.link(source, baseEntity)

		then:
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(director)
		1 * entityLookupByNameServiceMock.findStaffByName(DIRECTOR_NOT_FOUND, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		baseEntity.directors.size() == 1
		baseEntity.directors.contains director
	}

}
