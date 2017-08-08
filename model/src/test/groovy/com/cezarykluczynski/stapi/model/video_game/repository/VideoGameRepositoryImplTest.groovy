package com.cezarykluczynski.stapi.model.video_game.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame_
import com.cezarykluczynski.stapi.model.video_game.query.VideoGameQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractVideoGameTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class VideoGameRepositoryImplTest extends AbstractVideoGameTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private VideoGameQueryBuilderFactory videoGameQueryBuilderFactoryMock

	private VideoGameRepositoryImpl videoGameRepositoryImpl

	private QueryBuilder<VideoGame> videoGameQueryBuilder

	private Pageable pageable

	private VideoGameRequestDTO videoGameRequestDTO

	private VideoGame videoGame

	private Page page

	void setup() {
		videoGameQueryBuilderFactoryMock = Mock()
		videoGameRepositoryImpl = new VideoGameRepositoryImpl(videoGameQueryBuilderFactoryMock)
		videoGameQueryBuilder = Mock()
		pageable = Mock()
		videoGameRequestDTO = Mock()
		page = Mock()
		videoGame = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = videoGameRepositoryImpl.findMatching(videoGameRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * videoGameQueryBuilderFactoryMock.createQueryBuilder(pageable) >> videoGameQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * videoGameRequestDTO.uid >> UID
		1 * videoGameQueryBuilder.equal(VideoGame_.uid, UID)

		then: 'string criteria are set'
		1 * videoGameRequestDTO.title >> TITLE
		1 * videoGameQueryBuilder.like(VideoGame_.title, TITLE)

		then: 'date criteria are set'
		1 * videoGameRequestDTO.releaseDateFrom >> RELEASE_DATE_FROM
		1 * videoGameRequestDTO.releaseDateTo >> RELEASE_DATE_TO
		1 * videoGameQueryBuilder.between(VideoGame_.releaseDate, RELEASE_DATE_FROM, RELEASE_DATE_TO)

		then: 'sort is set'
		1 * videoGameRequestDTO.sort >> SORT
		1 * videoGameQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * videoGameQueryBuilder.fetch(VideoGame_.publishers, true)
		1 * videoGameQueryBuilder.fetch(VideoGame_.developers, true)
		1 * videoGameQueryBuilder.fetch(VideoGame_.platforms, true)
		1 * videoGameQueryBuilder.fetch(VideoGame_.genres, true)
		1 * videoGameQueryBuilder.fetch(VideoGame_.ratings, true)
		1 * videoGameQueryBuilder.fetch(VideoGame_.references, true)

		then: 'page is retrieved'
		1 * videoGameQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = videoGameRepositoryImpl.findMatching(videoGameRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * videoGameQueryBuilderFactoryMock.createQueryBuilder(pageable) >> videoGameQueryBuilder

		then: 'uid criteria is set to null'
		1 * videoGameRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * videoGameQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(videoGame)
		1 * videoGame.setPublishers(Sets.newHashSet())
		1 * videoGame.setDevelopers(Sets.newHashSet())
		1 * videoGame.setPlatforms(Sets.newHashSet())
		1 * videoGame.setGenres(Sets.newHashSet())
		1 * videoGame.setRatings(Sets.newHashSet())
		1 * videoGame.setReferences(Sets.newHashSet())
		pageOutput == page
	}

}
