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
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class WikitextToEntitiesProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextToEntitiesGenericProcessor wikitextToEntitiesGenericProcessorMock

	private LinkingTemplatesToEntitiesProcessor linkingTemplatesToEntitiesProcessorMock

	private WikitextApi wikitextApiMock

	private SeriesRepository seriesRepositoryMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessor

	void setup() {
		wikitextToEntitiesGenericProcessorMock = Mock()
		linkingTemplatesToEntitiesProcessorMock = Mock()
		wikitextApiMock = Mock()
		seriesRepositoryMock = Mock()
		wikitextToEntitiesProcessor = new WikitextToEntitiesProcessor(wikitextToEntitiesGenericProcessorMock, linkingTemplatesToEntitiesProcessorMock,
				wikitextApiMock, seriesRepositoryMock)
	}

	void "finds book series"() {
		given:
		List<BookSeries> bookSeriesList = Mock()

		when:
		List<BookSeries> bookSeriesListOutput = wikitextToEntitiesProcessor.findBookSeries(WIKITEXT)

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

	void "(wikitext) finds organizations"() {
		given:
		List<Organization> organizationList = Mock()

		when:
		List<Organization> organizationListOutput = wikitextToEntitiesProcessor.findOrganizations(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Organization) >> organizationList
		0 * _
		organizationListOutput == organizationList
	}

	void "(part) finds organizations"() {
		given:
		Template.Part part = new Template.Part()
		List<Organization> organizationList = Mock()

		when:
		List<Organization> organizationListOutput = wikitextToEntitiesProcessor.findOrganizations(part)

		then:
		1 * linkingTemplatesToEntitiesProcessorMock.process(part, Organization) >> organizationList
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
		Template template = new Template(title: TemplateTitle.S, parts: [
		        new Template.Part(value: 'TOS-R')
		])
		Template.Part templatePart = new Template.Part(value: WIKITEXT, templates: [template])
		Series tos = Mock()
		Series tas = Mock()
		Series tng = Mock()

		when:
		List<Series> seriesListOutput = wikitextToEntitiesProcessor.findSeries(templatePart)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Series) >> [tas]
		1 * wikitextApiMock.getPageTitlesFromWikitext(WIKITEXT) >> ['TNG']
		1 * seriesRepositoryMock.findByAbbreviation('TOS') >> Optional.of(tos)
		1 * seriesRepositoryMock.findByAbbreviation('TNG') >> Optional.of(tng)
		0 * _
		seriesListOutput == [tas, tos, tng]
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

	void "finds weapons"() {
		given:
		List<Weapon> weaponList = Mock()

		when:
		List<Weapon> weaponListOutput = wikitextToEntitiesProcessor.findWeapons(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Weapon) >> weaponList
		0 * _
		weaponListOutput == weaponList
	}

	void "finds technologies"() {
		given:
		List<Technology> technologyList = Mock()

		when:
		List<Technology> technologyListOutput = wikitextToEntitiesProcessor.findTechnology(WIKITEXT)

		then:
		1 * wikitextToEntitiesGenericProcessorMock.process(WIKITEXT, Technology) >> technologyList
		0 * _
		technologyListOutput == technologyList
	}

}
