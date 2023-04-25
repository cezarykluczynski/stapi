package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EntityLookupByNameService {

	private final GenericEntityLookupByNameService genericEntityLookupByNameService;

	public EntityLookupByNameService(GenericEntityLookupByNameService genericEntityLookupByNameService) {
		this.genericEntityLookupByNameService = genericEntityLookupByNameService;
	}

	// This is being used only by steps after CREATE_PERFORMERS, so it's OK to cache. But if usages extend into CREATE_PERFORMERS step,
	// some additional conditions should be implemented to not cache prematurely.
	@Cacheable(cacheNames = "entityLookupByNameCache", key = "@entityLookupByNameCacheService.resolveKey('performer', #p0, #p1)")
	public Optional<Performer> findPerformerByName(String performerName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(performerName, mediaWikiSource, Performer.class);
	}

	// This is being used only by steps after CREATE_CHARACTERS, so it's OK to cache. But if usages extend into CREATE_CHARACTERS step,
	// some additional conditions should be implemented to not cache prematurely.
	@Cacheable(cacheNames = "entityLookupByNameCache", key = "@entityLookupByNameCacheService.resolveKey('character', #p0, #p1)")
	public Optional<Character> findCharacterByName(String characterName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(characterName, mediaWikiSource, Character.class);
	}

	public Optional<Staff> findStaffByName(String staffName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(staffName, mediaWikiSource, Staff.class);
	}

	public Optional<Comics> findComicsByName(String comicsName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(comicsName, mediaWikiSource, Comics.class);
	}

	public Optional<AstronomicalObject> findAstronomicalObjectByName(String astronomicalObjectName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(astronomicalObjectName, mediaWikiSource, AstronomicalObject.class);
	}

	public Optional<Species> findSpeciesByName(String speciesName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(speciesName, mediaWikiSource, Species.class);
	}

	public Optional<Book> findBookByName(String speciesName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(speciesName, mediaWikiSource, Book.class);
	}

	public Optional<Series> findSeriesByName(String seriesName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(seriesName, mediaWikiSource, Series.class);
	}

	public Optional<SpacecraftClass> findSpacecraftClassByName(String spacecraftClassName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(spacecraftClassName, mediaWikiSource, SpacecraftClass.class);
	}

	public Optional<SpacecraftType> findSpacecraftTypeByName(String spacecraftTypeName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(spacecraftTypeName, mediaWikiSource, SpacecraftType.class);
	}

	public Optional<ComicSeries> findComicSeriesByName(String comicSeriesName, MediaWikiSource mediaWikiSource) {
		return genericEntityLookupByNameService.findEntityByName(comicSeriesName, mediaWikiSource, ComicSeries.class);
	}

}
