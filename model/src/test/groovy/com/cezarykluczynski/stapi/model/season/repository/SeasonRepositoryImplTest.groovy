package com.cezarykluczynski.stapi.model.season.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.entity.Season_
import com.cezarykluczynski.stapi.model.season.query.SeasonQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractSeasonTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class SeasonRepositoryImplTest extends AbstractSeasonTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private SeasonQueryBuilderFactory seasonQueryBuilderFactory

	private SeasonRepositoryImpl seasonRepositoryImpl

	private QueryBuilder<Season> seasonQueryBuilder

	private Pageable pageable

	private SeasonRequestDTO seasonRequestDTO

	private Page page

	void setup() {
		seasonQueryBuilderFactory = Mock()
		seasonRepositoryImpl = new SeasonRepositoryImpl(seasonQueryBuilderFactory)
		seasonQueryBuilder = Mock()
		pageable = Mock()
		seasonRequestDTO = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = seasonRepositoryImpl.findMatching(seasonRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * seasonQueryBuilderFactory.createQueryBuilder(pageable) >> seasonQueryBuilder

		then: 'uid criteria is set'
		1 * seasonRequestDTO.uid >> UID
		1 * seasonQueryBuilder.equal(Season_.uid, UID)

		then: 'string criteria are set'
		1 * seasonRequestDTO.title >> TITLE
		1 * seasonQueryBuilder.like(Season_.title, TITLE)

		then: 'integer criteria are set'
		1 * seasonRequestDTO.numberOfEpisodesFrom >> NUMBER_OF_EPISODES_FROM
		1 * seasonRequestDTO.numberOfEpisodesTo >> NUMBER_OF_EPISODES_TO
		1 * seasonQueryBuilder.between(Season_.numberOfEpisodes, NUMBER_OF_EPISODES_FROM, NUMBER_OF_EPISODES_TO)
		1 * seasonRequestDTO.seasonNumberFrom >> SEASON_NUMBER_FROM
		1 * seasonRequestDTO.seasonNumberTo >> SEASON_NUMBER_TO
		1 * seasonQueryBuilder.between(Season_.seasonNumber, SEASON_NUMBER_FROM, SEASON_NUMBER_TO)

		then: 'sort is set'
		1 * seasonRequestDTO.sort >> SORT
		1 * seasonQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * seasonQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
