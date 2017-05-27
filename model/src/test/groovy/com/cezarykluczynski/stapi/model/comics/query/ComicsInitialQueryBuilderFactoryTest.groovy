package com.cezarykluczynski.stapi.model.comics.query

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.comics.entity.Comics_
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractComicsTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class ComicsInitialQueryBuilderFactoryTest extends AbstractComicsTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private ComicsQueryBuilderFactory comicsQueryBuilderMock

	private ComicsInitialQueryBuilderFactory comicsInitialQueryBuilderFactory

	private QueryBuilder<Comics> comicsQueryBuilder

	private Pageable pageable

	private ComicsRequestDTO comicsRequestDTO

	private Comics comics

	private Page page

	void setup() {
		comicsQueryBuilderMock = Mock()
		comicsInitialQueryBuilderFactory = new ComicsInitialQueryBuilderFactory(comicsQueryBuilderMock)
		comicsQueryBuilder = Mock()
		pageable = Mock()
		comicsRequestDTO = Mock()
		page = Mock()
		comics = Mock()
	}

	void "initial query builder is built, then returned"() {
		when:
		QueryBuilder<Comics> comicsQueryBuilderOutput = comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable)

		then:
		1 * comicsQueryBuilderMock.createQueryBuilder(pageable) >> comicsQueryBuilder

		then: 'uid criteria is set'
		1 * comicsRequestDTO.uid >> UID
		1 * comicsQueryBuilder.equal(Comics_.uid, UID)

		then: 'string criteria are set'
		1 * comicsRequestDTO.title >> TITLE
		1 * comicsQueryBuilder.like(Comics_.title, TITLE)

		then: 'integer criteria are set'
		1 * comicsRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * comicsRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * comicsQueryBuilder.between(Comics_.publishedYear, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO)
		1 * comicsRequestDTO.numberOfPagesFrom >> NUMBER_OF_PAGES_FROM
		1 * comicsRequestDTO.numberOfPagesTo >> NUMBER_OF_PAGES_TO
		1 * comicsQueryBuilder.between(Comics_.numberOfPages, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO)

		1 * comicsRequestDTO.yearFrom >> YEAR_FROM
		1 * comicsQueryBuilder.between(Comics_.yearFrom, YEAR_FROM, null)
		1 * comicsRequestDTO.yearTo >> YEAR_TO
		1 * comicsQueryBuilder.between(Comics_.yearTo, null, YEAR_TO)

		then: 'float criteria are set'
		1 * comicsRequestDTO.stardateFrom >> STARDATE_FROM
		1 * comicsQueryBuilder.between(Comics_.stardateFrom, STARDATE_FROM, null)
		1 * comicsRequestDTO.stardateTo >> STARDATE_TO
		1 * comicsQueryBuilder.between(Comics_.stardateTo, null, STARDATE_TO)

		then: 'boolean criteria are set'
		1 * comicsRequestDTO.photonovel >> PHOTONOVEL
		1 * comicsQueryBuilder.equal(Comics_.photonovel, PHOTONOVEL)
		1 * comicsRequestDTO.adaptation >> ADAPTATION
		1 * comicsQueryBuilder.equal(Comics_.adaptation, ADAPTATION)

		then: 'sort is set'
		1 * comicsRequestDTO.sort >> SORT
		1 * comicsQueryBuilder.setSort(SORT)

		then: 'query builder is returned'
		comicsQueryBuilderOutput == comicsQueryBuilder

		then: 'no other interactions are expected'
		0 * _
	}

}
