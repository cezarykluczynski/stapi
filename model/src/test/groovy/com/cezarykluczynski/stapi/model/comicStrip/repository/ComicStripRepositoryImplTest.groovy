package com.cezarykluczynski.stapi.model.comicStrip.repository

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip_
import com.cezarykluczynski.stapi.model.comicStrip.query.ComicStripQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.util.AbstractComicStripTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class ComicStripRepositoryImplTest extends AbstractComicStripTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private ComicStripQueryBuilderFactory comicStripQueryBuilderFactory

	private ComicStripRepositoryImpl comicStripRepositoryImpl

	private QueryBuilder<ComicStrip> comicStripQueryBuilder

	private Pageable pageable

	private ComicStripRequestDTO comicStripRequestDTO

	private ComicStrip comicStrip

	private ComicStrip performersComicStrip

	private ComicStrip charactersComicStrip

	private Page page

	private Page performersPage

	private Page charactersPage

	private Set<Performer> performersSet

	private Set<Performer> stuntPerformersSet

	private Set<Performer> standInPerformersSet

	private Set<Character> charactersSet

	void setup() {
		comicStripQueryBuilderFactory = Mock(ComicStripQueryBuilderFactory)
		comicStripRepositoryImpl = new ComicStripRepositoryImpl(comicStripQueryBuilderFactory)
		comicStripQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		comicStripRequestDTO = Mock(ComicStripRequestDTO)
		page = Mock(Page)
		performersPage = Mock(Page)
		charactersPage = Mock(Page)
		comicStrip = Mock(ComicStrip)
		performersComicStrip = Mock(ComicStrip)
		charactersComicStrip = Mock(ComicStrip)
		performersSet = Mock(Set)
		stuntPerformersSet = Mock(Set)
		standInPerformersSet = Mock(Set)
		charactersSet = Mock(Set)
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = comicStripRepositoryImpl.findMatching(comicStripRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicStripQueryBuilderFactory.createQueryBuilder(pageable) >> comicStripQueryBuilder

		then: 'guid is retrieved, and it is not null'
		1 * comicStripRequestDTO.guid >> GUID
		1 * comicStripQueryBuilder.equal(ComicStrip_.guid, GUID)

		then: 'string criteria are set'
		1 * comicStripRequestDTO.title >> TITLE
		1 * comicStripQueryBuilder.like(ComicStrip_.title, TITLE)

		then: 'integer criteria are set'
		1 * comicStripRequestDTO.publishedYearFrom >> PUBLISHED_YEAR_FROM
		1 * comicStripQueryBuilder.between(ComicStrip_.publishedYearFrom, PUBLISHED_YEAR_FROM, null)
		1 * comicStripRequestDTO.publishedYearTo >> PUBLISHED_YEAR_TO
		1 * comicStripQueryBuilder.between(ComicStrip_.publishedYearTo, null, PUBLISHED_YEAR_TO)

		1 * comicStripRequestDTO.numberOfPagesFrom >> NUMBER_OF_PAGES_FROM
		1 * comicStripRequestDTO.numberOfPagesTo >> NUMBER_OF_PAGES_TO
		1 * comicStripQueryBuilder.between(ComicStrip_.numberOfPages, NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO)

		1 * comicStripRequestDTO.yearFrom >> YEAR_FROM
		1 * comicStripQueryBuilder.between(ComicStrip_.yearFrom, YEAR_FROM, null)
		1 * comicStripRequestDTO.yearTo >> YEAR_TO
		1 * comicStripQueryBuilder.between(ComicStrip_.yearTo, null, YEAR_TO)

		then: 'sort is set'
		1 * comicStripRequestDTO.sort >> SORT
		1 * comicStripQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * comicStripQueryBuilder.fetch(ComicStrip_.comicSeries, true)
		1 * comicStripQueryBuilder.fetch(ComicStrip_.writers, true)
		1 * comicStripQueryBuilder.fetch(ComicStrip_.artists, true)
		1 * comicStripQueryBuilder.fetch(ComicStrip_.characters, true)

		then: 'page is retrieved'
		1 * comicStripQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = comicStripRepositoryImpl.findMatching(comicStripRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicStripQueryBuilderFactory.createQueryBuilder(pageable) >> comicStripQueryBuilder

		then: 'guid criteria is set to null'
		1 * comicStripRequestDTO.guid >> null

		then: 'page is searched for and returned'
		1 * comicStripQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(comicStrip)
		1 * comicStrip.setComicSeries(Sets.newHashSet())
		1 * comicStrip.setWriters(Sets.newHashSet())
		1 * comicStrip.setArtists(Sets.newHashSet())
		1 * comicStrip.setCharacters(Sets.newHashSet())
		pageOutput == page
	}

}
