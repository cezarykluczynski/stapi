package com.cezarykluczynski.stapi.model.comic_collection.repository

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection_
import com.cezarykluczynski.stapi.model.comic_collection.query.ComicCollectionInitialQueryBuilderFactory
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class ComicCollectionRepositoryImplTest extends Specification {

	private static final String UID = 'ABCD0123456789'

	private ComicCollectionInitialQueryBuilderFactory comicCollectionInitialQueryBuilderFactory

	private ComicCollectionRepositoryImpl comicCollectionRepositoryImpl

	private QueryBuilder<ComicCollection> comicCollectionQueryBuilder

	private QueryBuilder<ComicCollection> comicCollectionComicSeriesPublishersComicsQueryBuilder

	private QueryBuilder<ComicCollection> comicCollectionCharactersReferencesQueryBuilder

	private Pageable pageable

	private ComicCollectionRequestDTO comicCollectionRequestDTO

	private ComicCollection comicCollection

	private ComicCollection comicSeriesPerformersComicCollection

	private ComicCollection charactersReferencesComicCollection

	private ComicCollection staffComicCollection

	private Page page

	private Page performersPage

	private Page charactersPage

	private Set<ComicSeries> comicSeriesSet

	private Set<Company> publishersSet

	private Set<Comics> comicsSet

	private Set<Character> charactersSet

	private Set<Reference> referencesSet

	private Set<Staff> writersSet

	private Set<Staff> artistsSet

	private Set<Staff> editorsSet

	void setup() {
		comicCollectionInitialQueryBuilderFactory = Mock()
		comicCollectionRepositoryImpl = new ComicCollectionRepositoryImpl(comicCollectionInitialQueryBuilderFactory)
		comicCollectionQueryBuilder = Mock()
		comicCollectionComicSeriesPublishersComicsQueryBuilder = Mock()
		comicCollectionCharactersReferencesQueryBuilder = Mock()
		pageable = Mock()
		comicCollectionRequestDTO = Mock()
		page = Mock()
		performersPage = Mock()
		charactersPage = Mock()
		comicCollection = Mock()
		comicSeriesPerformersComicCollection = Mock()
		charactersReferencesComicCollection = Mock()
		staffComicCollection = Mock()
		comicSeriesSet = Mock()
		publishersSet = Mock()
		comicsSet = Mock()
		charactersSet = Mock()
		referencesSet = Mock()
		writersSet = Mock()
		artistsSet = Mock()
		editorsSet = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = comicCollectionRepositoryImpl.findMatching(comicCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(comicCollectionRequestDTO, pageable) >>
				comicCollectionQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * comicCollectionRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.writers)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.artists)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.editors)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.staff)

		then: 'page is retrieved'
		1 * comicCollectionQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(comicCollection)

		then: 'another criteria builder is retrieved for comic series, publishers, and comics'
		1 * comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(comicCollectionRequestDTO, pageable) >>
				comicCollectionComicSeriesPublishersComicsQueryBuilder

		then: 'comic series and publishers fetch is performed'
		1 * comicCollectionComicSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.comicSeries)
		1 * comicCollectionComicSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.publishers)
		1 * comicCollectionComicSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.comics)

		then: 'comic series and publishers list is retrieved'
		1 * comicCollectionComicSeriesPublishersComicsQueryBuilder.findAll() >> Lists.newArrayList(comicSeriesPerformersComicCollection)

		then: 'comic series and publishers are set to comicCollection'
		1 * comicSeriesPerformersComicCollection.comicSeries >> comicSeriesSet
		1 * comicCollection.setComicSeries(comicSeriesSet)
		1 * comicSeriesPerformersComicCollection.publishers >> publishersSet
		1 * comicCollection.setPublishers(publishersSet)
		1 * comicSeriesPerformersComicCollection.comics >> comicsSet
		1 * comicCollection.setComics(comicsSet)

		then: 'another criteria builder is retrieved for characters and references'
		1 * comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(comicCollectionRequestDTO, pageable) >>
				comicCollectionCharactersReferencesQueryBuilder

		then: 'characters and references fetch is performed'
		1 * comicCollectionCharactersReferencesQueryBuilder.fetch(ComicCollection_.characters)
		1 * comicCollectionCharactersReferencesQueryBuilder.fetch(ComicCollection_.references)

		then: 'characters and references list is retrieved'
		1 * comicCollectionCharactersReferencesQueryBuilder.findAll() >> Lists.newArrayList(charactersReferencesComicCollection)

		then: 'characters and references are set to comicCollection'
		1 * charactersReferencesComicCollection.characters >> charactersSet
		1 * comicCollection.setCharacters(charactersSet)
		1 * charactersReferencesComicCollection.references >> referencesSet
		1 * comicCollection.setReferences(referencesSet)

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "query is built and performed without results from additional queries"() {
		when:
		Page pageOutput = comicCollectionRepositoryImpl.findMatching(comicCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(comicCollectionRequestDTO, pageable) >>
				comicCollectionQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * comicCollectionRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.writers)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.artists)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.editors)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.staff)

		then: 'page is retrieved'
		1 * comicCollectionQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(comicCollection)

		then: 'another criteria builder is retrieved for comic series and publishers'
		1 * comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(comicCollectionRequestDTO, pageable) >>
				comicCollectionComicSeriesPublishersComicsQueryBuilder

		then: 'comic series and publishers fetch is performed'
		1 * comicCollectionComicSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.comicSeries)
		1 * comicCollectionComicSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.publishers)
		1 * comicCollectionComicSeriesPublishersComicsQueryBuilder.fetch(ComicCollection_.comics)

		then: 'empty comic series and publishers list is retrieved'
		1 * comicCollectionComicSeriesPublishersComicsQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'another criteria builder is retrieved for characters and references'
		1 * comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(comicCollectionRequestDTO, pageable) >>
				comicCollectionCharactersReferencesQueryBuilder

		then: 'characters and references fetch is performed'
		1 * comicCollectionCharactersReferencesQueryBuilder.fetch(ComicCollection_.characters)
		1 * comicCollectionCharactersReferencesQueryBuilder.fetch(ComicCollection_.references)

		then: 'empty characters and references list is retrieved'
		1 * comicCollectionCharactersReferencesQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "empty page is returned"() {
		when:
		Page pageOutput = comicCollectionRepositoryImpl.findMatching(comicCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(comicCollectionRequestDTO, pageable) >>
				comicCollectionQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * comicCollectionRequestDTO.uid >> UID

		then: 'staff fetch is performed'
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.writers)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.artists)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.editors)
		1 * comicCollectionQueryBuilder.fetch(ComicCollection_.staff)

		then: 'page is retrieved'
		1 * comicCollectionQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = comicCollectionRepositoryImpl.findMatching(comicCollectionRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * comicCollectionInitialQueryBuilderFactory.createInitialQueryBuilder(comicCollectionRequestDTO, pageable) >> comicCollectionQueryBuilder

		then: 'uid criteria is set to null'
		1 * comicCollectionRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * comicCollectionQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(comicCollection)
		comicCollection.setComicSeries(Sets.newHashSet())
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
