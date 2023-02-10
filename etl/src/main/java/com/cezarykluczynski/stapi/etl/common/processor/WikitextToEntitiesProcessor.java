package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.util.constant.SeriesAbbreviation;
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
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WikitextToEntitiesProcessor {

	private final WikitextToEntitiesGenericProcessor wikitextToEntitiesGenericProcessor;

	private final LinkingTemplatesToEntitiesProcessor linkingTemplatesToEntitiesProcessor;

	private final WikitextApi wikitextApi;

	private final SeriesRepository seriesRepository;

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

	public List<Organization> findOrganizations(Template.Part part) {
		return linkingTemplatesToEntitiesProcessor.process(part, Organization.class);
	}

	public List<Title> findTitles(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Title.class);
	}

	public List<Season> findSeasons(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Season.class);
	}

	public List<Series> findSeries(Template.Part part) {
		String value = part.getValue();
		List<Series> series = wikitextToEntitiesGenericProcessor.process(value, Series.class);
		final List<String> pageTitlesFromWikitext = wikitextApi.getPageTitlesFromWikitext(value);
		part.getTemplates()
				.stream().filter(template -> TemplateTitle.S.equals(template.getTitle()))
				.filter(template -> !template.getParts().isEmpty())
				.map(template -> template.getParts().get(0).getValue())
				.forEach(pageTitlesFromWikitext::add);
		for (Map.Entry<String, String> variantToCanonicalAbbreviation : SeriesAbbreviation.SERIES_VARIANTS_TO_CANONICAL_ABBREVIATIONS.entrySet()) {
			for (String pageTitleFromWikitext : pageTitlesFromWikitext) {
				if (StringUtils.equals(variantToCanonicalAbbreviation.getKey(), pageTitleFromWikitext)) {
					seriesRepository.findByAbbreviation(variantToCanonicalAbbreviation.getValue()).ifPresent(series::add);
				}
			}
		}
		return series;
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

	public List<Weapon> findWeapons(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Weapon.class);
	}

	public List<Technology> findTechnology(String item) {
		return wikitextToEntitiesGenericProcessor.process(item, Technology.class);
	}

}
