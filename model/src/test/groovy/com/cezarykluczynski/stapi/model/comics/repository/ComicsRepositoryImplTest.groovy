package com.cezarykluczynski.stapi.model.comics.repository

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.comics.entity.Comics_
import com.cezarykluczynski.stapi.model.comics.query.ComicsQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.util.AbstractComicsTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class ComicsRepositoryImplTest extends AbstractComicsTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private ComicsQueryBuilderFactory comicsQueryBuilderFactory

	private ComicsRepositoryImpl comicsRepositoryImpl

	private QueryBuilder<Comics> comicsQueryBuilder

	private Pageable pageable

	private ComicsRequestDTO comicsRequestDTO

	private Comics comics

	private Page page

	void setup() {
		comicsQueryBuilderFactory = Mock()
		comicsRepositoryImpl = new ComicsRepositoryImpl(comicsQueryBuilderFactory)
		comicsQueryBuilder = Mock()
		pageable = Mock()
		comicsRequestDTO = Mock()
		page = Mock()
		comics = Mock()

	}

	void "query is built and performed"() {
		when:
		Page pageOutput = comicsRepositoryImpl.findMatching(comicsRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicsQueryBuilderFactory.createQueryBuilder(pageable) >> comicsQueryBuilder

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

		then: 'fetch is performed'
		1 * comicsQueryBuilder.fetch(Comics_.writers, true)
		1 * comicsQueryBuilder.fetch(Comics_.artists, true)
		1 * comicsQueryBuilder.fetch(Comics_.editors, true)
		1 * comicsQueryBuilder.divideQueries()
		1 * comicsQueryBuilder.fetch(Comics_.staff, true)
		1 * comicsQueryBuilder.divideQueries()
		1 * comicsQueryBuilder.fetch(Comics_.comicSeries, true)
		1 * comicsQueryBuilder.fetch(Comics_.publishers, true)
		1 * comicsQueryBuilder.fetch(Comics_.comicCollections, true)
		1 * comicsQueryBuilder.divideQueries()
		1 * comicsQueryBuilder.fetch(Comics_.characters, true)
		1 * comicsQueryBuilder.divideQueries()
		1 * comicsQueryBuilder.fetch(Comics_.references, true)

		then: 'page is retrieved'
		1 * comicsQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = comicsRepositoryImpl.findMatching(comicsRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicsQueryBuilderFactory.createQueryBuilder(pageable) >> comicsQueryBuilder

		then: 'uid criteria is set to null'
		1 * comicsRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * comicsQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(comics)
		1 * comics.setWriters(Sets.newHashSet())
		1 * comics.setArtists(Sets.newHashSet())
		1 * comics.setEditors(Sets.newHashSet())
		1 * comics.setStaff(Sets.newHashSet())
		1 * comics.setComicSeries(Sets.newHashSet())
		1 * comics.setPublishers(Sets.newHashSet())
		1 * comics.setCharacters(Sets.newHashSet())
		1 * comics.setReferences(Sets.newHashSet())
		1 * comics.setComicCollections(Sets.newHashSet())
		pageOutput == page
	}

}
