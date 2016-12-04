package com.cezarykluczynski.stapi.etl.episode.creation.service

import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesToEpisodeBindingServiceTest extends Specification {

	private static final String TOS = 'TOS'
	private static final String TAS = 'TAS'

	private SeriesRepository seriesRepositoryMock

	private SeriesToEpisodeBindingService seriesToEpisodeBindingService

	def setup() {
		seriesRepositoryMock = Mock(SeriesRepository)
		seriesToEpisodeBindingService = new SeriesToEpisodeBindingService(seriesRepositoryMock)
	}

	def "gets series from category"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(
						title: CategoryName.TOS_EPISODES
				)
		)
		Series seriesTos = new Series(
				abbreviation: TOS
		)
		List<Series> seriesList = Lists.newArrayList(seriesTos)

		when:
		Series seriesOutput = seriesToEpisodeBindingService.mapCategoriesToSeries(categoryHeaderList)

		then:
		1 * seriesRepositoryMock.findAll() >> seriesList
		seriesOutput == seriesTos
	}

	def "returns null when there is more than one series category"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(
						title: CategoryName.TOS_EPISODES
				),
				new CategoryHeader(
						title: CategoryName.TAS_EPISODES
				)
		)
		List<Series> seriesList = Lists.newArrayList(
				new Series(
						abbreviation: TOS
				),
				new Series(
						abbreviation: TAS
				)
		)

		when:
		Series seriesOutput = seriesToEpisodeBindingService.mapCategoriesToSeries(categoryHeaderList)

		then:
		1 * seriesRepositoryMock.findAll() >> seriesList
		seriesOutput == null
	}

	def "returns null when series cannot be found"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(
						title: CategoryName.TOS_EPISODES
				)
		)
		List<Series> seriesList = Lists.newArrayList()

		when:
		Series seriesOutput = seriesToEpisodeBindingService.mapCategoriesToSeries(categoryHeaderList)

		then:
		1 * seriesRepositoryMock.findAll() >> seriesList
		seriesOutput == null
	}

}
