package com.cezarykluczynski.stapi.model.soundtrack.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.soundtrack.dto.SoundtrackRequestDTO
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack_
import com.cezarykluczynski.stapi.model.soundtrack.query.SoundtrackQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractSoundtrackTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class SoundtrackRepositoryImplTest extends AbstractSoundtrackTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private SoundtrackQueryBuilderFactory soundtrackQueryBuilderFactoryMock

	private SoundtrackRepositoryImpl soundtrackRepositoryImpl

	private QueryBuilder<Soundtrack> soundtrackQueryBuilder

	private Pageable pageable

	private SoundtrackRequestDTO soundtrackRequestDTO

	private Soundtrack soundtrack

	private Page page

	void setup() {
		soundtrackQueryBuilderFactoryMock = Mock()
		soundtrackRepositoryImpl = new SoundtrackRepositoryImpl(soundtrackQueryBuilderFactoryMock)
		soundtrackQueryBuilder = Mock()
		pageable = Mock()
		soundtrackRequestDTO = Mock()
		page = Mock()
		soundtrack = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = soundtrackRepositoryImpl.findMatching(soundtrackRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * soundtrackQueryBuilderFactoryMock.createQueryBuilder(pageable) >> soundtrackQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * soundtrackRequestDTO.uid >> UID
		1 * soundtrackQueryBuilder.equal(Soundtrack_.uid, UID)

		then: 'string criteria are set'
		1 * soundtrackRequestDTO.title >> TITLE
		1 * soundtrackQueryBuilder.like(Soundtrack_.title, TITLE)

		then: 'date criteria are set'
		1 * soundtrackRequestDTO.releaseDateFrom >> RELEASE_DATE_FROM
		1 * soundtrackRequestDTO.releaseDateTo >> RELEASE_DATE_TO
		1 * soundtrackQueryBuilder.between(Soundtrack_.releaseDate, RELEASE_DATE_FROM, RELEASE_DATE_TO)

		then: 'integer criteria are set'
		1 * soundtrackRequestDTO.lengthFrom >> LENGTH_FROM
		1 * soundtrackRequestDTO.lengthTo >> LENGTH_TO
		1 * soundtrackQueryBuilder.between(Soundtrack_.length, LENGTH_FROM, LENGTH_TO)

		then: 'sort is set'
		1 * soundtrackRequestDTO.sort >> SORT
		1 * soundtrackQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * soundtrackQueryBuilder.fetch(Soundtrack_.labels, true)
		1 * soundtrackQueryBuilder.fetch(Soundtrack_.composers, true)
		1 * soundtrackQueryBuilder.fetch(Soundtrack_.contributors, true)
		1 * soundtrackQueryBuilder.fetch(Soundtrack_.orchestrators, true)
		1 * soundtrackQueryBuilder.fetch(Soundtrack_.references, true)

		then: 'page is retrieved'
		1 * soundtrackQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = soundtrackRepositoryImpl.findMatching(soundtrackRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * soundtrackQueryBuilderFactoryMock.createQueryBuilder(pageable) >> soundtrackQueryBuilder

		then: 'uid criteria is set to null'
		1 * soundtrackRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * soundtrackQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(soundtrack)
		1 * soundtrack.setLabels(Sets.newHashSet())
		1 * soundtrack.setComposers(Sets.newHashSet())
		1 * soundtrack.setContributors(Sets.newHashSet())
		1 * soundtrack.setOrchestrators(Sets.newHashSet())
		1 * soundtrack.setReferences(Sets.newHashSet())
		pageOutput == page
	}

}
