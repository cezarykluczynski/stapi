package com.cezarykluczynski.stapi.model.comic_collection.repository

import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection_
import com.cezarykluczynski.stapi.model.comic_collection.query.ComicCollectionQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractComicCollectionTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class ComicCollectionRepositoryImplTest extends AbstractComicCollectionTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private ComicCollectionQueryBuilderFactory comicCollectionQueryBuilderFactory

	private ComicCollectionRepositoryImpl comicCollectionRepositoryImpl

	private QueryBuilder<ComicCollection> comicCollectionQueryBuilder

	private Pageable pageable

	private ComicCollectionRequestDTO comicCollectionRequestDTO

	private ComicCollection comicCollection

	private Page page

	void setup() {
		comicCollectionQueryBuilderFactory = Mock()
		comicCollectionRepositoryImpl = new ComicCollectionRepositoryImpl(comicCollectionQueryBuilderFactory)
		comicCollectionQueryBuilder = Mock()
		pageable = Mock()
		comicCollectionRequestDTO = Mock()
		page = Mock()
		comicCollection = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = comicCollectionRepositoryImpl.findMatching(comicCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicCollectionQueryBuilderFactory.createQueryBuilder(pageable) >> comicCollectionQueryBuilder

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

		then: 'fetch is performed'
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.writers, true)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.editors, true)
		1 * comicCollectionQueryBuilder.divideQueries()
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.artists, true)
		1 * comicCollectionQueryBuilder.divideQueries()
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.staff, true)
		1 * comicCollectionQueryBuilder.divideQueries()
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.comicSeries, true)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.childComicSeries, true)
		1 * comicCollectionQueryBuilder.divideQueries()
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.publishers, true)
		1 * comicCollectionQueryBuilder.divideQueries()
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.comics, true)
		1 * comicCollectionQueryBuilder.divideQueries()
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.characters, true)
		1 * comicCollectionQueryBuilder.divideQueries()
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.references, true)

		then: 'page is retrieved'
		1 * comicCollectionQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = comicCollectionRepositoryImpl.findMatching(comicCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicCollectionQueryBuilderFactory.createQueryBuilder(pageable) >> comicCollectionQueryBuilder

		then: 'uid criteria is set to null'
		1 * comicCollectionRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * comicCollectionQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(comicCollection)
		comicCollection.setComicSeries(Sets.newHashSet())
		comicCollection.setChildComicSeries(Sets.newHashSet())
		comicCollection.setWriters(Sets.newHashSet())
		comicCollection.setArtists(Sets.newHashSet())
		comicCollection.setEditors(Sets.newHashSet())
		comicCollection.setStaff(Sets.newHashSet())
		comicCollection.setPublishers(Sets.newHashSet())
		comicCollection.setCharacters(Sets.newHashSet())
		comicCollection.setReferences(Sets.newHashSet())
		comicCollection.setComics(Sets.newHashSet())
		pageOutput == page
	}

}
