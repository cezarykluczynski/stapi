package com.cezarykluczynski.stapi.model.comic_collection.query

import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection_
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractComicCollectionTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class ComicCollectionInitialQueryBuilderFactoryTest extends AbstractComicCollectionTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderMock

	private ComicCollectionInitialQueryBuilderFactory comicCollectionInitialQueryBuilderFactory

	private QueryBuilder<ComicCollection> comicCollectionQueryBuilder

	private Pageable pageable

	private ComicCollectionRequestDTO comicCollectionRequestDTO

	private ComicCollection comicCollection

	private Page page

	void setup() {
		comicCollectionQueryBuilderMock = Mock()
		comicCollectionInitialQueryBuilderFactory = new ComicCollectionInitialQueryBuilderFactory(comicCollectionQueryBuilderMock)
		comicCollectionQueryBuilder = Mock()
		pageable = Mock()
		comicCollectionRequestDTO = Mock()
		page = Mock()
		comicCollection = Mock()
	}

	void "initial query builder is built, then returned"() {
		when:
		QueryBuilder<ComicCollection> comicCollectionQueryBuilderOutput = comicCollectionInitialQueryBuilderFactory
				.createInitialQueryBuilder(comicCollectionRequestDTO, pageable)

		then:
		1 * comicCollectionQueryBuilderMock.createQueryBuilder(pageable) >> comicCollectionQueryBuilder

		then: 'uid criteria is set'
		1 * comicCollectionRequestDTO.uid >> UID
		1 * comicCollectionQueryBuilder.equal(ComicCollection_.uid, UID)

		then: 'string criteria are set'
		1 * comicCollectionRequestDTO.title >> TITLE
		1 * comicCollectionQueryBuilder.like(ComicCollection_.title, TITLE)

		then: 'integer criteria are set'
		1 * comicCollectionRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * comicCollectionRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * comicCollectionQueryBuilder.between(ComicCollection_.publishedYear, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO)
		1 * comicCollectionRequestDTO.numberOfPagesFrom >> NUMBER_OF_PAGES_FROM
		1 * comicCollectionRequestDTO.numberOfPagesTo >> NUMBER_OF_PAGES_TO
		1 * comicCollectionQueryBuilder.between(ComicCollection_.numberOfPages, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO)

		1 * comicCollectionRequestDTO.yearFrom >> YEAR_FROM
		1 * comicCollectionQueryBuilder.between(ComicCollection_.yearFrom, YEAR_FROM, null)
		1 * comicCollectionRequestDTO.yearTo >> YEAR_TO
		1 * comicCollectionQueryBuilder.between(ComicCollection_.yearTo, null, YEAR_TO)

		then: 'float criteria are set'
		1 * comicCollectionRequestDTO.stardateFrom >> STARDATE_FROM
		1 * comicCollectionQueryBuilder.between(ComicCollection_.stardateFrom, STARDATE_FROM, null)
		1 * comicCollectionRequestDTO.stardateTo >> STARDATE_TO
		1 * comicCollectionQueryBuilder.between(ComicCollection_.stardateTo, null, STARDATE_TO)

		then: 'boolean criteria are set'
		1 * comicCollectionRequestDTO.photonovel >> PHOTONOVEL
		1 * comicCollectionQueryBuilder.equal(ComicCollection_.photonovel, PHOTONOVEL)

		then: 'sort is set'
		1 * comicCollectionRequestDTO.sort >> SORT
		1 * comicCollectionQueryBuilder.setSort(SORT)

		then: 'query builder is returned'
		comicCollectionQueryBuilderOutput == comicCollectionQueryBuilder

		then: 'no other interactions are expected'
		0 * _
	}

}
