package com.cezarykluczynski.stapi.model.comic_strip.repository

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip_
import com.cezarykluczynski.stapi.model.comic_strip.query.ComicStripQueryBuilderFactory
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
		comicStripQueryBuilderFactory = Mock()
		comicStripRepositoryImpl = new ComicStripRepositoryImpl(comicStripQueryBuilderFactory)
		comicStripQueryBuilder = Mock()
		pageable = Mock()
		comicStripRequestDTO = Mock()
		page = Mock()
		performersPage = Mock()
		charactersPage = Mock()
		comicStrip = Mock()
		performersComicStrip = Mock()
		charactersComicStrip = Mock()
		performersSet = Mock()
		stuntPerformersSet = Mock()
		standInPerformersSet = Mock()
		charactersSet = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = comicStripRepositoryImpl.findMatching(comicStripRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicStripQueryBuilderFactory.createQueryBuilder(pageable) >> comicStripQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * comicStripRequestDTO.uid >> UID
		1 * comicStripQueryBuilder.equal(ComicStrip_.uid, UID)

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

		then: 'uid criteria is set to null'
		1 * comicStripRequestDTO.uid >> null

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
