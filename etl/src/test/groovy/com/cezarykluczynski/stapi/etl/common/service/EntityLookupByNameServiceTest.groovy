package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import spock.lang.Specification

class EntityLookupByNameServiceTest extends Specification {

	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN
	private static final String CHARACTER_NAME = 'CHARACTER_NAME'
	private static final String PERFORMER_NAME = 'PERFORMER_NAME'
	private static final String STAFF_NAME = 'STAFF_NAME'
	private static final String COMICS_NAME = 'COMICS_NAME'
	private static final String ASTRONOMICAL_OBJECT_NAME = 'ASTRONOMICAL_OBJECT_NAME'
	private static final String SPECIES_NAME = 'SPECIES_NAME'
	private static final String BOOK_TITLE = 'BOOK_TITLE'
	private static final String SERIES_TITLE = 'SERIES_TITLE'
	private static final String SPACECRAFT_CLASS_TITLE = 'SPACECRAFT_CLASS_TITLE'
	private static final String SPACECRAFT_TYPE_TITLE = 'SPACECRAFT_TYPE_TITLE'
	private static final String COMIC_SERIES_TITLE = 'COMIC_SERIES_TITLE'

	private GenericEntityLookupByNameService genericEntityLookupByNameService

	private EntityLookupByNameService entityLookupByNameService

	void setup() {
		genericEntityLookupByNameService = Mock()
		entityLookupByNameService = new EntityLookupByNameService(genericEntityLookupByNameService)
	}

	void "gets performer from generic service"() {
		given:
		Performer performer = Mock()

		when:
		Optional<Performer> performerOptional = entityLookupByNameService.findPerformerByName(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE, Performer) >> Optional.of(performer)
		0 * _
		performerOptional.get() == performer
	}

	void "gets character from generic service"() {
		given:
		Character character = Mock()

		when:
		Optional<Character> characterOptional = entityLookupByNameService.findCharacterByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE, Character) >> Optional.of(character)
		0 * _
		characterOptional.get() == character
	}

	void "gets staff from generic service"() {
		given:
		Staff staff = Mock()

		when:
		Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE, Staff) >> Optional.of(staff)
		0 * _
		staffOptional.get() == staff
	}

	void "gets comics from generic service"() {
		given:
		Comics comics = Mock()

		when:
		Optional<Comics> comicsOptional = entityLookupByNameService.findComicsByName(COMICS_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(COMICS_NAME, SOURCES_MEDIA_WIKI_SOURCE, Comics) >> Optional.of(comics)
		0 * _
		comicsOptional.get() == comics
	}

	void "gets astronomical object from generic service"() {
		given:
		AstronomicalObject astronomicalObject = Mock()

		when:
		Optional<AstronomicalObject> comicsOptional = entityLookupByNameService
				.findAstronomicalObjectByName(ASTRONOMICAL_OBJECT_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(ASTRONOMICAL_OBJECT_NAME, SOURCES_MEDIA_WIKI_SOURCE, AstronomicalObject) >> Optional
				.of(astronomicalObject)
		0 * _
		comicsOptional.get() == astronomicalObject
	}

	void "gets species object from generic service"() {
		given:
		Species species = Mock()

		when:
		Optional<Species> speciesOptional = entityLookupByNameService.findSpeciesByName(SPECIES_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(SPECIES_NAME, SOURCES_MEDIA_WIKI_SOURCE, Species) >> Optional.of(species)
		0 * _
		speciesOptional.get() == species
	}

	void "gets book object from generic service"() {
		given:
		Book book = Mock()

		when:
		Optional<Book> bookOptional = entityLookupByNameService.findBookByName(BOOK_TITLE, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(BOOK_TITLE, SOURCES_MEDIA_WIKI_SOURCE, Book) >> Optional.of(book)
		0 * _
		bookOptional.get() == book
	}

	void "gets series object from generic service"() {
		given:
		Series series = Mock()

		when:
		Optional<Series> seriesOptional = entityLookupByNameService.findSeriesByName(SERIES_TITLE, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(SERIES_TITLE, SOURCES_MEDIA_WIKI_SOURCE, Series) >> Optional.of(series)
		0 * _
		seriesOptional.get() == series
	}

	void "gets spacecraft class object from generic service"() {
		given:
		SpacecraftClass spacecraftClass = Mock()

		when:
		Optional<SpacecraftClass> spacecraftClassOptional = entityLookupByNameService
				.findSpacecraftClassByName(SPACECRAFT_CLASS_TITLE, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(SPACECRAFT_CLASS_TITLE, SOURCES_MEDIA_WIKI_SOURCE, SpacecraftClass) >>
				Optional.of(spacecraftClass)
		0 * _
		spacecraftClassOptional.get() == spacecraftClass
	}

	void "gets spacecraft type object from generic service"() {
		given:
		SpacecraftType spacecraftType = Mock()

		when:
		Optional<SpacecraftType> spacecraftTypeOptional = entityLookupByNameService
				.findSpacecraftTypeByName(SPACECRAFT_TYPE_TITLE, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(SPACECRAFT_TYPE_TITLE, SOURCES_MEDIA_WIKI_SOURCE, SpacecraftType) >>
				Optional.of(spacecraftType)
		0 * _
		spacecraftTypeOptional.get() == spacecraftType
	}

	void "gets comic series object from generic service"() {
		given:
		ComicSeries comicSeries = Mock()

		when:
		Optional<ComicSeries> comicSeriesOptional = entityLookupByNameService
				.findComicSeriesByName(COMIC_SERIES_TITLE, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * genericEntityLookupByNameService.findEntityByName(COMIC_SERIES_TITLE, SOURCES_MEDIA_WIKI_SOURCE, ComicSeries) >>
				Optional.of(comicSeries)
		0 * _
		comicSeriesOptional.get() == comicSeries
	}

}
