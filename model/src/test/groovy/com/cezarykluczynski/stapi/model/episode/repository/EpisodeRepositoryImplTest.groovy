package com.cezarykluczynski.stapi.model.episode.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.episode.entity.Episode_
import com.cezarykluczynski.stapi.model.episode.query.EpisodeQueryBuilderFactory
import com.cezarykluczynski.stapi.model.season.entity.Season_
import com.cezarykluczynski.stapi.model.series.entity.Series_
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDate

class EpisodeRepositoryImplTest extends Specification {

	private static final String UID = 'ABCD0123456789'
	private static final String TITLE = 'TITLE'
	private static final String PRODUCTION_SERIAL_NUMBER = 'PRODUCTION_SERIAL_NUMBER'
	private static final Integer SEASON_NUMBER_FROM = 1
	private static final Integer SEASON_NUMBER_TO = 2
	private static final Integer EPISODE_NUMBER_FROM = 3
	private static final Integer EPISODE_NUMBER_TO = 4
	private static final Float STARDATE_FROM = (Float) 3.3
	private static final Float STARDATE_TO = (Float) 4.6
	private static final LocalDate US_AIR_DATE_FROM = LocalDate.of(1968, 3, 4)
	private static final LocalDate US_AIR_DATE_TO = LocalDate.of(1968, 5, 6)
	private static final LocalDate FINAL_SCRIPT_DATE_FROM = LocalDate.of(1967, 10, 11)
	private static final LocalDate FINAL_SCRIPT_DATE_TO = LocalDate.of(1967, 11, 12)
	private static final Boolean FEATURE_LENGTH = RandomUtil.nextBoolean()
	private static final Integer YEAR_FROM = 2250
	private static final Integer YEAR_TO = 2370
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private EpisodeQueryBuilderFactory episodeQueryBuilderFactoryMock

	private EpisodeRepositoryImpl episodeRepositoryImpl

	private QueryBuilder<Episode> episodeQueryBuilder

	private Pageable pageable

	private EpisodeRequestDTO episodeRequestDTO

	private Episode episode

	private Page page

	void setup() {
		episodeQueryBuilderFactoryMock = Mock()
		episodeRepositoryImpl = new EpisodeRepositoryImpl(episodeQueryBuilderFactoryMock)
		episodeQueryBuilder = Mock()
		pageable = Mock()
		episodeRequestDTO = Mock()
		page = Mock()
		episode = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = episodeRepositoryImpl.findMatching(episodeRequestDTO, pageable)

		then:
		1 * episodeQueryBuilderFactoryMock.createQueryBuilder(pageable) >> episodeQueryBuilder

		then: 'uid criteria is set'
		1 * episodeRequestDTO.uid >> UID
		1 * episodeQueryBuilder.equal(Episode_.uid, UID)

		then: 'string criteria are set'
		1 * episodeRequestDTO.title >> TITLE
		1 * episodeQueryBuilder.like(Episode_.title, TITLE)
		1 * episodeRequestDTO.productionSerialNumber >> PRODUCTION_SERIAL_NUMBER
		1 * episodeQueryBuilder.like(Episode_.productionSerialNumber, PRODUCTION_SERIAL_NUMBER)

		then: 'integer criteria are set'
		1 * episodeRequestDTO.seasonNumberFrom >> SEASON_NUMBER_FROM
		1 * episodeRequestDTO.seasonNumberTo >> SEASON_NUMBER_TO
		1 * episodeQueryBuilder.between(Episode_.seasonNumber, SEASON_NUMBER_FROM, SEASON_NUMBER_TO)
		1 * episodeRequestDTO.episodeNumberFrom >> EPISODE_NUMBER_FROM
		1 * episodeRequestDTO.episodeNumberTo >> EPISODE_NUMBER_TO
		1 * episodeQueryBuilder.between(Episode_.episodeNumber, EPISODE_NUMBER_FROM, EPISODE_NUMBER_TO)
		1 * episodeRequestDTO.yearFrom >> YEAR_FROM
		1 * episodeQueryBuilder.between(Episode_.yearFrom, YEAR_FROM, null)
		1 * episodeRequestDTO.yearTo >> YEAR_TO
		1 * episodeQueryBuilder.between(Episode_.yearTo, null, YEAR_TO)

		then: 'float criteria are set'
		1 * episodeRequestDTO.stardateFrom >> STARDATE_FROM
		1 * episodeQueryBuilder.between(Episode_.stardateFrom, STARDATE_FROM, null)
		1 * episodeRequestDTO.stardateTo >> STARDATE_TO
		1 * episodeQueryBuilder.between(Episode_.stardateTo, null, STARDATE_TO)

		then: 'boolean criteria are set'
		1 * episodeRequestDTO.featureLength >> FEATURE_LENGTH
		1 * episodeQueryBuilder.equal(Episode_.featureLength, FEATURE_LENGTH)

		then: 'date criteria are set'
		1 * episodeRequestDTO.usAirDateFrom >> US_AIR_DATE_FROM
		1 * episodeRequestDTO.usAirDateTo >> US_AIR_DATE_TO
		1 * episodeQueryBuilder.between(Episode_.usAirDate, US_AIR_DATE_FROM, US_AIR_DATE_TO)
		1 * episodeRequestDTO.finalScriptDateFrom >> FINAL_SCRIPT_DATE_FROM
		1 * episodeRequestDTO.finalScriptDateTo >> FINAL_SCRIPT_DATE_TO
		1 * episodeQueryBuilder.between(Episode_.finalScriptDate, FINAL_SCRIPT_DATE_FROM, FINAL_SCRIPT_DATE_TO)

		then: 'sort is set'
		1 * episodeRequestDTO.sort >> SORT
		1 * episodeQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * episodeQueryBuilder.fetch(Episode_.series)

		then: 'fetch is performed with true flag'
		1 * episodeQueryBuilder.fetch(Episode_.series, Series_.productionCompany, true)
		1 * episodeQueryBuilder.fetch(Episode_.series, Series_.originalBroadcaster, true)
		1 * episodeQueryBuilder.fetch(Episode_.season, true)
		1 * episodeQueryBuilder.fetch(Episode_.season, Season_.series, true)
		1 * episodeQueryBuilder.fetch(Episode_.writers, true)
		1 * episodeQueryBuilder.fetch(Episode_.teleplayAuthors, true)
		1 * episodeQueryBuilder.fetch(Episode_.storyAuthors, true)
		1 * episodeQueryBuilder.fetch(Episode_.directors, true)
		1 * episodeQueryBuilder.fetch(Episode_.staff, true)
		1 * episodeQueryBuilder.fetch(Episode_.performers, true)
		1 * episodeQueryBuilder.fetch(Episode_.stuntPerformers, true)
		1 * episodeQueryBuilder.fetch(Episode_.standInPerformers, true)
		1 * episodeQueryBuilder.fetch(Episode_.characters, true)

		then: 'page is searched for and returned'
		1 * episodeQueryBuilder.findPage() >> page
		0 * page.content
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = episodeRepositoryImpl.findMatching(episodeRequestDTO, pageable)

		then:
		1 * episodeQueryBuilderFactoryMock.createQueryBuilder(pageable) >> episodeQueryBuilder

		then: 'uid criteria is set to null'
		1 * episodeRequestDTO.uid >> null

		then: 'fetch is performed'
		1 * episodeQueryBuilder.fetch(Episode_.series)

		then: 'fetch is performed with false flag'
		1 * episodeQueryBuilder.fetch(Episode_.series, Series_.productionCompany, false)
		1 * episodeQueryBuilder.fetch(Episode_.series, Series_.originalBroadcaster, false)
		1 * episodeQueryBuilder.fetch(Episode_.season, false)
		1 * episodeQueryBuilder.fetch(Episode_.season, Season_.series, false)
		1 * episodeQueryBuilder.fetch(Episode_.writers, false)
		1 * episodeQueryBuilder.fetch(Episode_.teleplayAuthors, false)
		1 * episodeQueryBuilder.fetch(Episode_.storyAuthors, false)
		1 * episodeQueryBuilder.fetch(Episode_.directors, false)
		1 * episodeQueryBuilder.fetch(Episode_.staff, false)
		1 * episodeQueryBuilder.fetch(Episode_.performers, false)
		1 * episodeQueryBuilder.fetch(Episode_.stuntPerformers, false)
		1 * episodeQueryBuilder.fetch(Episode_.standInPerformers, false)
		1 * episodeQueryBuilder.fetch(Episode_.characters, false)

		then: 'page is searched for and returned'
		1 * episodeQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(episode)
		1 * episode.setWriters(Sets.newHashSet())
		1 * episode.setTeleplayAuthors(Sets.newHashSet())
		1 * episode.setStoryAuthors(Sets.newHashSet())
		1 * episode.setDirectors(Sets.newHashSet())
		1 * episode.setStaff(Sets.newHashSet())
		1 * episode.setPerformers(Sets.newHashSet())
		1 * episode.setStuntPerformers(Sets.newHashSet())
		1 * episode.setStandInPerformers(Sets.newHashSet())
		1 * episode.setCharacters(Sets.newConcurrentHashSet())
		pageOutput == page
	}

}
