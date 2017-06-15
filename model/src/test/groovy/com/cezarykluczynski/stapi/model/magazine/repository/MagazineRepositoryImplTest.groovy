package com.cezarykluczynski.stapi.model.magazine.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine_
import com.cezarykluczynski.stapi.model.magazine.query.MagazineQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractMagazineTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class MagazineRepositoryImplTest extends AbstractMagazineTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private MagazineQueryBuilderFactory magazineQueryBuilderFactoryMock

	private MagazineRepositoryImpl magazineRepositoryImpl

	private QueryBuilder<Magazine> magazineQueryBuilder

	private Pageable pageable

	private MagazineRequestDTO magazineRequestDTO

	private Magazine magazine

	private Page page

	void setup() {
		magazineQueryBuilderFactoryMock = Mock()
		magazineRepositoryImpl = new MagazineRepositoryImpl(magazineQueryBuilderFactoryMock)
		magazineQueryBuilder = Mock()
		pageable = Mock()
		magazineRequestDTO = Mock()
		magazine = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = magazineRepositoryImpl.findMatching(magazineRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * magazineQueryBuilderFactoryMock.createQueryBuilder(pageable) >> magazineQueryBuilder

		then: 'uid criteria is set'
		1 * magazineRequestDTO.uid >> UID
		1 * magazineQueryBuilder.equal(Magazine_.uid, UID)

		then: 'string criteria are set'
		1 * magazineRequestDTO.title >> TITLE
		1 * magazineQueryBuilder.like(Magazine_.title, TITLE)

		then: 'integer criteria are set'
		1 * magazineRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * magazineRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * magazineQueryBuilder.between(Magazine_.publishedYear, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO)
		1 * magazineRequestDTO.numberOfPagesFrom >> NUMBER_OF_PAGES_FROM
		1 * magazineRequestDTO.numberOfPagesTo >> NUMBER_OF_PAGES_TO
		1 * magazineQueryBuilder.between(Magazine_.numberOfPages, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO)

		then: 'sort is set'
		1 * magazineRequestDTO.sort >> SORT
		1 * magazineQueryBuilder.setSort(SORT)

		then: 'fetch is performed with true flag'
		1 * magazineQueryBuilder.fetch(Magazine_.magazineSeries, true)
		1 * magazineQueryBuilder.fetch(Magazine_.editors, true)
		1 * magazineQueryBuilder.fetch(Magazine_.publishers, true)

		then: 'page is retrieved'
		1 * magazineQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = magazineRepositoryImpl.findMatching(magazineRequestDTO, pageable)

		then:
		1 * magazineQueryBuilderFactoryMock.createQueryBuilder(pageable) >> magazineQueryBuilder

		then: 'uid criteria is set to null'
		1 * magazineRequestDTO.uid >> null

		then: 'fetch is performed with false flag'
		1 * magazineQueryBuilder.fetch(Magazine_.magazineSeries, false)
		1 * magazineQueryBuilder.fetch(Magazine_.editors, false)
		1 * magazineQueryBuilder.fetch(Magazine_.publishers, false)

		then: 'page is searched for and returned'
		1 * magazineQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(magazine)
		1 * magazine.setMagazineSeries(Sets.newHashSet())
		1 * magazine.setEditors(Sets.newHashSet())
		1 * magazine.setPublishers(Sets.newHashSet())
		pageOutput == page
	}

}
