package com.cezarykluczynski.stapi.model.video_release.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.season.entity.Season_
import com.cezarykluczynski.stapi.model.series.entity.Series_
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease_
import com.cezarykluczynski.stapi.model.video_release.query.VideoReleaseQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractVideoReleaseTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
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

		then: 'sort is set'
		1 * videoReleaseRequestDTO.sort >> SORT
		1 * videoReleaseQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.series)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.series, Series_.productionCompany, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.series, Series_.originalBroadcaster, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.season)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.season, Season_.series, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.references, true)
		1 * videoReleaseQueryBuilder.fetch(VideoRelease_.ratings, true)
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

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = videoReleaseRepositoryImpl.findMatching(videoReleaseRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * videoReleaseQueryBuilderFactoryMock.createQueryBuilder(pageable) >> videoReleaseQueryBuilder

		then: 'uid criteria is set to null'
		1 * videoReleaseRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * videoReleaseQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(videoRelease)
		1 * videoRelease.setReferences(Sets.newHashSet())
		1 * videoRelease.setRatings(Sets.newHashSet())
		1 * videoRelease.setLanguages(Sets.newHashSet())
		1 * videoRelease.setLanguagesSubtitles(Sets.newHashSet())
		1 * videoRelease.setLanguagesDubbed(Sets.newHashSet())
		pageOutput == page
	}

}
