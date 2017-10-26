package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WikitextToEntitiesProcessor {

	private final WikitextToEntitiesGenericProcessor wikitextToEntitiesGenericProcessor;

	public WikitextToEntitiesProcessor(WikitextToEntitiesGenericProcessor wikitextToEntitiesGenericProcessor) {
		this.wikitextToEntitiesGenericProcessor = wikitextToEntitiesGenericProcessor;
	}

	public List<BookSeries> findBookSeries(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, BookSeries.class);
	}

	public List<Staff> findStaff(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Staff.class);
	}

	public List<Character> findCharacters(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Character.class);
	}

	public List<ComicSeries> findComicSeries(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, ComicSeries.class);
	}

	public List<Company> findCompanies(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Company.class);
	}

	public List<MagazineSeries> findMagazineSeries(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, MagazineSeries.class);
	}

	public List<Organization> findOrganizations(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Organization.class);
	}

	public List<Title> findTitles(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Title.class);
	}

	public List<Season> findSeasons(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Season.class);
	}

	public List<Series> findSeries(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Series.class);
	}

	public List<SpacecraftClass> findSpacecraftClasses(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, SpacecraftClass.class);
	}

	public List<Location> findLocations(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Location.class);
	}

	public List<Occupation> findOccupations(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Occupation.class);
	}

}
