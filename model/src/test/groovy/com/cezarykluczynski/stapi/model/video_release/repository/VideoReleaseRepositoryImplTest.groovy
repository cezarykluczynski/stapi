package com.cezarykluczynski.stapi.model.video_release.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease_
import com.cezarykluczynski.stapi.model.video_release.query.VideoReleaseQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractVideoReleaseTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class VideoReleaseRepositoryImplTest extends AbstractVideoReleaseTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private VideoReleaseQueryBuilderFactory videoReleaseQueryBuilderFactoryMock

	private VideoReleaseRepositoryImpl videoReleaseRepositoryImpl

	private QueryBuilder<VideoRelease> videoReleaseQueryBuilder

	private Pageable pageable

	private VideoReleaseRequestDTO videoReleaseRequestDTO

	private VideoRelease videoRelease

	private Page page

	void setup() {
		videoReleaseQueryBuilderFactoryMock = Mock()
		videoReleaseRepositoryImpl = new VideoReleaseRepositoryImpl(videoReleaseQueryBuilderFactoryMock)
		videoReleaseQueryBuilder = Mock()
		pageable = Mock()
		videoReleaseRequestDTO = Mock()
		page = Mock()
		videoRelease = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = videoReleaseRepositoryImpl.findMatching(videoReleaseRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * videoReleaseQueryBuilderFactoryMock.createQueryBuilder(pageable) >> videoReleaseQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * videoReleaseRequestDTO.uid >> UID
		1 * videoReleaseQueryBuilder.equal(VideoRelease_.uid, UID)

		then: 'string criteria are set'
		1 * videoReleaseRequestDTO.title >> TITLE
		1 * videoReleaseQueryBuilder.like(VideoRelease_.title, TITLE)

		then: 'integer criteria are set'
		1 * videoReleaseRequestDTO.yearFrom >> YEAR_FROM
		1 * videoReleaseQueryBuilder.between(VideoRelease_.yearFrom, YEAR_FROM, null)
		1 * videoReleaseRequestDTO.yearTo >> YEAR_TO
		1 * videoReleaseQueryBuilder.between(VideoRelease_.yearTo, null, YEAR_TO)

		1 * videoReleaseRequestDTO.runTimeFrom >> RUN_TIME_FROM
		1 * videoReleaseRequestDTO.runTimeTo >> RUN_TIME_TO
		1 * videoReleaseQueryBuilder.between(VideoRelease_.runTime, RUN_TIME_FROM, RUN_TIME_TO)

		then: 'boolean criteria are set'
		1 * videoReleaseRequestDTO.documentary >> DOCUMENTARY
		1 * videoReleaseQueryBuilder.equal(VideoRelease_.documentary, DOCUMENTARY)
		1 * videoReleaseRequestDTO.specialFeatures >> SPECIAL_FEATURES
		1 * videoReleaseQueryBuilder.equal(VideoRelease_.specialFeatures, SPECIAL_FEATURES)

		then: 'sort is set'
		1 * videoReleaseRequestDTO.sort >> SORT
		1 * videoReleaseQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.series, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.seasons, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.movies, true)
		1 * videoReleaseQueryBuilder.divideQueries()
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.references, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.ratings, true)
		1 * videoReleaseQueryBuilder.divideQueries()
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.languages, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.languagesSubtitles, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.languagesDubbed, true)

		then: 'page is retrieved'
		1 * videoReleaseQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
