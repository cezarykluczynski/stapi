package com.cezarykluczynski.stapi.model.title.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.model.title.entity.Title_
import com.cezarykluczynski.stapi.model.title.query.TitleQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractTitleTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class TitleRepositoryImplTest extends AbstractTitleTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private TitleQueryBuilderFactory titleQueryBuilderFactoryMock

	private TitleRepositoryImpl titleRepositoryImpl

	private QueryBuilder<Title> titleQueryBuilder

	private Pageable pageable

	private TitleRequestDTO titleRequestDTO

	private Title title

	private Page page

	void setup() {
		titleQueryBuilderFactoryMock = Mock()
		titleRepositoryImpl = new TitleRepositoryImpl(titleQueryBuilderFactoryMock)
		titleQueryBuilder = Mock()
		pageable = Mock()
		titleRequestDTO = Mock()
		title = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = titleRepositoryImpl.findMatching(titleRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * titleQueryBuilderFactoryMock.createQueryBuilder(pageable) >> titleQueryBuilder

		then: 'uid criteria is set'
		1 * titleRequestDTO.uid >> UID
		1 * titleQueryBuilder.equal(Title_.uid, UID)

		then: 'string criteria are set'
		1 * titleRequestDTO.name >> NAME
		1 * titleQueryBuilder.like(Title_.name, NAME)

		then: 'boolean criteria are set'
		1 * titleRequestDTO.militaryRank >> MILITARY_RANK
		1 * titleQueryBuilder.equal(Title_.militaryRank, MILITARY_RANK)
		1 * titleRequestDTO.fleetRank >> FLEET_RANK
		1 * titleQueryBuilder.equal(Title_.fleetRank, FLEET_RANK)
		1 * titleRequestDTO.religiousTitle >> RELIGIOUS_TITLE
		1 * titleQueryBuilder.equal(Title_.religiousTitle, RELIGIOUS_TITLE)
		1 * titleRequestDTO.position >> POSITION
		1 * titleQueryBuilder.equal(Title_.position, POSITION)
		1 * titleRequestDTO.mirror >> MIRROR
		1 * titleQueryBuilder.equal(Title_.mirror, MIRROR)

		then: 'sort is set'
		1 * titleRequestDTO.sort >> SORT
		1 * titleQueryBuilder.setSort(SORT)

		then: 'fetch is performed with true flag'
		1 * titleQueryBuilder.fetch(Title_.characters, true)

		then: 'page is retrieved'
		1 * titleQueryBuilder.findPage() >> page
		0 * page.content

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = titleRepositoryImpl.findMatching(titleRequestDTO, pageable)

		then:
		1 * titleQueryBuilderFactoryMock.createQueryBuilder(pageable) >> titleQueryBuilder

		then: 'uid criteria is set to null'
		1 * titleRequestDTO.uid >> null

		then: 'fetch is performed with false flag'
		1 * titleQueryBuilder.fetch(Title_.characters, false)

		then: 'page is searched for and returned'
		1 * titleQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(title)
		1 * title.setCharacters(Sets.newHashSet())
		pageOutput == page
	}

}
