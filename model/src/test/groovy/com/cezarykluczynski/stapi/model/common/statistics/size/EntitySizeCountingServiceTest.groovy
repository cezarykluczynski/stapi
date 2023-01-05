package com.cezarykluczynski.stapi.model.common.statistics.size

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity
import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository
import com.google.common.collect.Maps
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import org.springframework.data.jpa.repository.JpaContext
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification

class EntitySizeCountingServiceTest extends Specification {

	private static final long SERIES_COUNT = 10
	private static final long SPECIES_COUNT = 20

	private RepositoryProvider repositoryProviderMock

	private JpaContext jpaContextMock

	private EntitySizeCountingService entitySizeCountingService

	void setup() {
		repositoryProviderMock = Mock()
		jpaContextMock = Mock()
		entitySizeCountingService = new EntitySizeCountingService(repositoryProviderMock, jpaContextMock)
	}

	void "provides map of entity count"() {
		given:
		SeriesRepository seriesRepository = Mock()
		SpeciesRepository speciesRepository = Mock()
		Map<Class<? extends PageAwareEntity>, CrudRepository> repositoriesMap = Maps.newHashMap()
		repositoriesMap.put(Series, seriesRepository)
		repositoriesMap.put(Species, speciesRepository)

		when:
		Map<Class<? extends PageAwareEntity>, Long> statisticsMap = entitySizeCountingService.countEntities()

		then:
		1 * repositoryProviderMock.provide() >> repositoriesMap
		1 * seriesRepository.count() >> SERIES_COUNT
		1 * speciesRepository.count() >> SPECIES_COUNT
		0 * _
		statisticsMap.size() == 2
		statisticsMap.get(Series) == SERIES_COUNT
		statisticsMap.get(Species) == SPECIES_COUNT
	}

	void "provides relations count"() {
		given:
		VideoReleaseRepository videoReleaseRepository = Mock()
		SeasonRepository seasonRepository = Mock()
		EntityManager entityManager = Mock()
		Map<Class<? extends PageAwareEntity>, CrudRepository> repositoriesMap = Maps.newLinkedHashMap()
		repositoriesMap.put(VideoRelease, videoReleaseRepository)
		repositoriesMap.put(Season, seasonRepository)
		Query query = Mock()

		when:
		Long countRelations = entitySizeCountingService.countRelations()

		then:
		1 * repositoryProviderMock.provide() >> repositoriesMap

		and:
		1 * jpaContextMock.getEntityManagerByManagedType(Season) >> entityManager
		1 * entityManager.createQuery('select count(*) from Season where series is not null') >> query
		1 * query.singleResult >> 3L

		and:
		1 * jpaContextMock.getEntityManagerByManagedType(VideoRelease) >> entityManager
		1 * entityManager.createQuery('select count(*) from VideoRelease where series is not null') >> query
		1 * query.singleResult >> 5L

		and:
		1 * jpaContextMock.getEntityManagerByManagedType(VideoRelease) >> entityManager
		1 * entityManager.createQuery('select count(*) from VideoRelease where season is not null') >> query
		1 * query.singleResult >> 7L

		and:
		1 * jpaContextMock.getEntityManagerByManagedType(VideoRelease) >> entityManager
		1 * entityManager.createNativeQuery('select count(*) from stapi.video_releases_references') >> query
		1 * query.singleResult >> 11L

		and:
		1 * jpaContextMock.getEntityManagerByManagedType(VideoRelease) >> entityManager
		1 * entityManager.createNativeQuery('select count(*) from stapi.video_releases_ratings') >> query
		1 * query.singleResult >> 13L

		and:
		1 * jpaContextMock.getEntityManagerByManagedType(VideoRelease) >> entityManager
		1 * entityManager.createNativeQuery('select count(*) from stapi.video_releases_languages') >> query
		1 * query.singleResult >> 17L

		and:
		1 * jpaContextMock.getEntityManagerByManagedType(VideoRelease) >> entityManager
		1 * entityManager.createNativeQuery('select count(*) from stapi.video_releases_languages_sub') >> query
		1 * query.singleResult >> 19L

		and:
		1 * jpaContextMock.getEntityManagerByManagedType(VideoRelease) >> entityManager
		1 * entityManager.createNativeQuery('select count(*) from stapi.video_releases_languages_dub') >> query
		1 * query.singleResult >> 23L

		and:
		0 * _
		countRelations == 98L
	}

}
