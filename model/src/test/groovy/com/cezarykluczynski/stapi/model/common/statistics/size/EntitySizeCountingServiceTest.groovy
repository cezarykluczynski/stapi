package com.cezarykluczynski.stapi.model.common.statistics.size

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity
import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import com.google.common.collect.Maps
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification

class EntitySizeCountingServiceTest extends Specification {

	private static final long SERIES_COUNT = 10
	private static final long SPECIES_COUNT = 20

	private RepositoryProvider repositoryProviderMock

	private EntitySizeCountingService entitySizeCountingService

	void setup() {
		repositoryProviderMock = Mock()
		entitySizeCountingService = new EntitySizeCountingService(repositoryProviderMock)
	}

	void "provides map of entity count"() {
		given:
		SeriesRepository seriesRepository = Mock()
		SpeciesRepository speciesRepository = Mock()
		Map<Class<? extends PageAwareEntity>, CrudRepository> repositoriesMap = Maps.newHashMap()
		repositoriesMap.put(Series, seriesRepository)
		repositoriesMap.put(Species, speciesRepository)

		when:
		Map<Class<? extends PageAwareEntity>, Long> statisticsMap = entitySizeCountingService.count()

		then:
		1 * repositoryProviderMock.provide() >> repositoriesMap
		1 * seriesRepository.count() >> SERIES_COUNT
		1 * speciesRepository.count() >> SPECIES_COUNT
		0 * _
		statisticsMap.size() == 2
		statisticsMap.get(Series) == SERIES_COUNT
		statisticsMap.get(Species) == SPECIES_COUNT
	}

}
