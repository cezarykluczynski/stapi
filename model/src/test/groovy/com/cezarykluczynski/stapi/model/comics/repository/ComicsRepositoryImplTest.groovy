package com.cezarykluczynski.stapi.model.comics.repository

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.comics.entity.Comics_
import com.cezarykluczynski.stapi.model.comics.query.ComicsInitialQueryBuilderFactory
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class ComicsRepositoryImplTest extends Specification {

	private static final String GUID = 'ABCD0123456789'

	private ComicsInitialQueryBuilderFactory comicsInitialQueryBuilderFactory

	private ComicsRepositoryImpl comicsRepositoryImpl

	private QueryBuilder<Comics> comicsQueryBuilder

	private QueryBuilder<Comics> comicsComicSeriesPublishersQueryBuilder

	private QueryBuilder<Comics> comicsCharactersReferencesQueryBuilder

	private Pageable pageable

	private ComicsRequestDTO comicsRequestDTO

	private Comics comics

	private Comics comicSeriesPerformersComics

	private Comics charactersReferencesComics

	private Page page

	private Page performersPage

	private Page charactersPage

	private Set<ComicSeries> comicSeriesSet

	private Set<Company> publishersSet

	private Set<Character> charactersSet

	private Set<Reference> referencesSet

	void setup() {
		comicsInitialQueryBuilderFactory = Mock(ComicsInitialQueryBuilderFactory)
		comicsRepositoryImpl = new ComicsRepositoryImpl(comicsInitialQueryBuilderFactory)
		comicsQueryBuilder = Mock(QueryBuilder)
		comicsComicSeriesPublishersQueryBuilder = Mock(QueryBuilder)
		comicsCharactersReferencesQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		comicsRequestDTO = Mock(ComicsRequestDTO)
		page = Mock(Page)
		performersPage = Mock(Page)
		charactersPage = Mock(Page)
		comics = Mock(Comics)
		comicSeriesPerformersComics = Mock(Comics)
		charactersReferencesComics = Mock(Comics)
		comicSeriesSet = Mock(Set)
		publishersSet = Mock(Set)
		charactersSet = Mock(Set)
		referencesSet = Mock(Set)
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = comicsRepositoryImpl.findMatching(comicsRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable) >> comicsQueryBuilder

		then: 'guid is retrieved, and it is not null'
		1 * comicsRequestDTO.guid >> GUID

		then: 'staff fetch is performed'
		1 * comicsQueryBuilder.fetch(Comics_.writers)
		1 * comicsQueryBuilder.fetch(Comics_.artists)
		1 * comicsQueryBuilder.fetch(Comics_.editors)
		1 * comicsQueryBuilder.fetch(Comics_.staff)

		then: 'page is retrieved'
		1 * comicsQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(comics)

		then: 'another criteria builder is retrieved for comic series and publishers'
		1 * comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable) >> comicsComicSeriesPublishersQueryBuilder

		then: 'comic series and publishers fetch is performed'
		1 * comicsComicSeriesPublishersQueryBuilder.fetch(Comics_.comicSeries)
		1 * comicsComicSeriesPublishersQueryBuilder.fetch(Comics_.publishers)

		then: 'comic series and publishers list is retrieved'
		1 * comicsComicSeriesPublishersQueryBuilder.findAll() >> Lists.newArrayList(comicSeriesPerformersComics)

		then: 'comic series and publishers are set to comics'
		1 * comicSeriesPerformersComics.comicSeries >> comicSeriesSet
		1 * comics.setComicSeries(comicSeriesSet)
		1 * comicSeriesPerformersComics.publishers >> publishersSet
		1 * comics.setPublishers(publishersSet)

		then: 'another criteria builder is retrieved for characters and references'
		1 * comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable) >> comicsCharactersReferencesQueryBuilder

		then: 'characters and references fetch is performed'
		1 * comicsCharactersReferencesQueryBuilder.fetch(Comics_.characters)
		1 * comicsCharactersReferencesQueryBuilder.fetch(Comics_.references)

		then: 'characters and references list is retrieved'
		1 * comicsCharactersReferencesQueryBuilder.findAll() >> Lists.newArrayList(charactersReferencesComics)

		then: 'characters and references are set to comics'
		1 * charactersReferencesComics.characters >> charactersSet
		1 * comics.setCharacters(charactersSet)
		1 * charactersReferencesComics.references >> referencesSet
		1 * comics.setReferences(referencesSet)

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "query is built and performed without results from additional queries"() {
		when:
		Page pageOutput = comicsRepositoryImpl.findMatching(comicsRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable) >> comicsQueryBuilder

		then: 'guid is retrieved, and it is not null'
		1 * comicsRequestDTO.guid >> GUID

		then: 'staff fetch is performed'
		1 * comicsQueryBuilder.fetch(Comics_.writers)
		1 * comicsQueryBuilder.fetch(Comics_.artists)
		1 * comicsQueryBuilder.fetch(Comics_.editors)
		1 * comicsQueryBuilder.fetch(Comics_.staff)

		then: 'page is retrieved'
		1 * comicsQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(comics)

		then: 'another criteria builder is retrieved for comic series and publishers'
		1 * comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable) >> comicsComicSeriesPublishersQueryBuilder

		then: 'comic series and publishers fetch is performed'
		1 * comicsComicSeriesPublishersQueryBuilder.fetch(Comics_.comicSeries)
		1 * comicsComicSeriesPublishersQueryBuilder.fetch(Comics_.publishers)

		then: 'empty comic series and publishers list is retrieved'
		1 * comicsComicSeriesPublishersQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'another criteria builder is retrieved for characters and references'
		1 * comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable) >> comicsCharactersReferencesQueryBuilder

		then: 'characters and references fetch is performed'
		1 * comicsCharactersReferencesQueryBuilder.fetch(Comics_.characters)
		1 * comicsCharactersReferencesQueryBuilder.fetch(Comics_.references)

		then: 'empty characters and references list is retrieved'
		1 * comicsCharactersReferencesQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "empty page is returned"() {
		when:
		Page pageOutput = comicsRepositoryImpl.findMatching(comicsRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable) >> comicsQueryBuilder

		then: 'guid is retrieved, and it is not null'
		1 * comicsRequestDTO.guid >> GUID

		then: 'staff fetch is performed'
		1 * comicsQueryBuilder.fetch(Comics_.writers)
		1 * comicsQueryBuilder.fetch(Comics_.artists)
		1 * comicsQueryBuilder.fetch(Comics_.editors)
		1 * comicsQueryBuilder.fetch(Comics_.staff)

		then: 'page is retrieved'
		1 * comicsQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = comicsRepositoryImpl.findMatching(comicsRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicsInitialQueryBuilderFactory.createInitialQueryBuilder(comicsRequestDTO, pageable) >> comicsQueryBuilder

		then: 'guid criteria is set to null'
		1 * comicsRequestDTO.guid >> null

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
		pageOutput == page
	}

}
