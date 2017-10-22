package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.title.entity.Title
import spock.lang.Specification

class WikitextToEntitiesProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextToEntitiesGenericProcessor wikitextToEntitiesGenericProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessor

	void setup() {
		wikitextToEntitiesGenericProcessorMock = Mock()
		wikitextToEntitiesProcessor = new WikitextToEntitiesProcessor(wikitextToEntitiesGenericProcessorMock)
	}

	void "finds book series"() {
		given:
		List<BookSeries> bookSeriesList = Mock()

		when:
		List<BookSeries> bookSeriesListOutput = wikitextToEntitiesProcessor.findBookSeries(WIKITEXT,)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, BookSeries) >> bookSeriesList
		0 * _
		bookSeriesListOutput == bookSeriesList
	}

	void "finds staff"() {
		given:
		List<Staff> staffList = Mock()

		when:
		List<Staff> staffListOutput = wikitextToEntitiesProcessor.findStaff(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Staff) >> staffList
		0 * _
		staffListOutput == staffList
	}

	void "finds characters"() {
		given:
		List<Character> characterList = Mock()

		when:
		List<Character> characterListOutput = wikitextToEntitiesProcessor.findCharacters(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Character) >> characterList
		0 * _
		characterListOutput == characterList
	}

	void "finds comic series"() {
		given:
		List<ComicSeries> comicSeriesList = Mock()

		when:
		List<ComicSeries> comicSeriesListOutput = wikitextToEntitiesProcessor.findComicSeries(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, ComicSeries) >> comicSeriesList
		0 * _
		comicSeriesListOutput == comicSeriesList
	}

	void "finds companies"() {
		given:
		List<Company> companyList = Mock()

		when:
		List<Company> companyListOutput = wikitextToEntitiesProcessor.findCompanies(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Company) >> companyList
		0 * _
		companyListOutput == companyList
	}

	void "finds magazine series"() {
		given:
		List<MagazineSeries> magazineSeriesList = Mock()

		when:
		List<MagazineSeries> magazineSeriesListOutput = wikitextToEntitiesProcessor.findMagazineSeries(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, MagazineSeries) >> magazineSeriesList
		0 * _
		magazineSeriesListOutput == magazineSeriesList
	}

	void "finds organizations"() {
		given:
		List<Organization> organizationList = Mock()

		when:
		List<Organization> organizationListOutput = wikitextToEntitiesProcessor.findOrganizations(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Organization) >> organizationList
		0 * _
		organizationListOutput == organizationList
	}

	void "finds titles"() {
		given:
		List<Title> titleList = Mock()

		when:
		List<Title> titleListOutput = wikitextToEntitiesProcessor.findTitles(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Title) >> titleList
		0 * _
		titleListOutput == titleList
	}

	void "finds seasons"() {
		given:
		List<Season> seasonList = Mock()

		when:
		List<Season> seasonListOutput = wikitextToEntitiesProcessor.findSeasons(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Season) >> seasonList
		0 * _
		seasonListOutput == seasonList
	}

	void "finds series"() {
		given:
		List<Series> seriesList = Mock()

		when:
		List<Series> seriesListOutput = wikitextToEntitiesProcessor.findSeries(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Series) >> seriesList
		0 * _
		seriesListOutput == seriesList
	}

	void "finds spacecraft classes"() {
		given:
		List<SpacecraftClass> spacecraftClassList = Mock()

		when:
		List<SpacecraftClass> spacecraftClassListOutput = wikitextToEntitiesProcessor.findSpacecraftClasses(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, SpacecraftClass) >> spacecraftClassList
		0 * _
		spacecraftClassListOutput == spacecraftClassList
	}

	void "finds locations"() {
		given:
		List<Location> locationClassList = Mock()

		when:
		List<Location> locationClassListOutput = wikitextToEntitiesProcessor.findLocations(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Location) >> locationClassList
		0 * _
		locationClassListOutput == locationClassList
	}

	void "finds occupations"() {
		given:
		List<Occupation> occupationClassList = Mock()

		when:
		List<Occupation> occupationClassListOutput = wikitextToEntitiesProcessor.findOccupations(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Occupation) >> occupationClassList
		0 * _
		occupationClassListOutput == occupationClassList
	}

}
