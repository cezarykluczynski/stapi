package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomicalObject.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import java.util.Optional;

@Service
@Slf4j
public class EntityLookupByNameService {

	private PageApi pageApi;

	private CharacterRepository characterRepository;

	private PerformerRepository performerRepository;

	private StaffRepository staffRepository;

	private ComicsRepository comicsRepository;

	private AstronomicalObjectRepository astronomicalObjectRepository;

	private MediaWikiSourceMapper mediaWikiSourceMapper;

	@Inject
	public EntityLookupByNameService(PageApi pageApi, CharacterRepository characterRepository, PerformerRepository performerRepository,
			StaffRepository staffRepository, ComicsRepository comicsRepository, AstronomicalObjectRepository astronomicalObjectRepository,
			MediaWikiSourceMapper mediaWikiSourceMapper) {
		this.pageApi = pageApi;
		this.characterRepository = characterRepository;
		this.performerRepository = performerRepository;
		this.staffRepository = staffRepository;
		this.comicsRepository = comicsRepository;
		this.astronomicalObjectRepository = astronomicalObjectRepository;
		this.mediaWikiSourceMapper = mediaWikiSourceMapper;
	}

	public Optional<Performer> findPerformerByName(String performerName, MediaWikiSource mediaWikiSource) {
		Optional<Performer> performerOptional;

		try {
			performerOptional = performerRepository.findByPageTitleAndPageMediaWikiSource(performerName,
					mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource));
		} catch (NonUniqueResultException e) {
			performerOptional = Optional.empty();
		}

		if (performerOptional.isPresent()) {
			return performerOptional;
		} else {
			Page page = pageApi.getPage(performerName, mediaWikiSource);
			if (page != null) {
				return performerRepository.findByPagePageIdAndPageMediaWikiSource(page.getPageId(),
						mediaWikiSourceMapper.fromSourcesToEntity(page.getMediaWikiSource()));
			}
		}

		return Optional.empty();
	}

	public Optional<Character> findCharacterByName(String characterName, MediaWikiSource mediaWikiSource) {
		Optional<Character> characterOptional;

		try {
			characterOptional = characterRepository.findByPageTitleAndPageMediaWikiSource(characterName,
					mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource));
		} catch (NonUniqueResultException e) {
			characterOptional = Optional.empty();
		}

		if (characterOptional.isPresent()) {
			return characterOptional;
		} else {
			Page page = pageApi.getPage(characterName, mediaWikiSource);
			if (page != null) {
				return characterRepository.findByPagePageIdAndPageMediaWikiSource(page.getPageId(),
						mediaWikiSourceMapper.fromSourcesToEntity(page.getMediaWikiSource()));
			}
		}

		return Optional.empty();
	}

	public Optional<Staff> findStaffByName(String staffName, MediaWikiSource mediaWikiSource) {
		Optional<Staff> staffOptional;

		try {
			staffOptional = staffRepository.findByPageTitleAndPageMediaWikiSource(staffName,
					mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource));
		} catch (NonUniqueResultException e) {
			staffOptional = Optional.empty();
		}

		if (staffOptional.isPresent()) {
			return staffOptional;
		} else {
			Page page = pageApi.getPage(staffName, mediaWikiSource);
			if (page != null) {
				return staffRepository.findByPagePageIdAndPageMediaWikiSource(page.getPageId(),
						mediaWikiSourceMapper.fromSourcesToEntity(page.getMediaWikiSource()));
			}
		}

		return Optional.empty();
	}

	public Optional<Comics> findComicsByName(String comicsName, MediaWikiSource mediaWikiSource) {
		Optional<Comics> comicsOptional;

		try {
			comicsOptional = comicsRepository.findByPageTitleAndPageMediaWikiSource(comicsName,
					mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource));
		} catch (NonUniqueResultException e) {
			comicsOptional = Optional.empty();
		}

		if (comicsOptional.isPresent()) {
			return comicsOptional;
		} else {
			Page page = pageApi.getPage(comicsName, mediaWikiSource);
			if (page != null) {
				return comicsRepository.findByPagePageIdAndPageMediaWikiSource(page.getPageId(),
						mediaWikiSourceMapper.fromSourcesToEntity(page.getMediaWikiSource()));
			}
		}

		return Optional.empty();
	}

	public Optional<AstronomicalObject> findAstronomicalObjectByName(String astronomicalObjectName, MediaWikiSource mediaWikiSource) {
		Optional<AstronomicalObject> astronomicalObjectOptional;

		try {
			astronomicalObjectOptional = astronomicalObjectRepository.findByPageTitleAndPageMediaWikiSource(astronomicalObjectName,
					mediaWikiSourceMapper.fromSourcesToEntity(mediaWikiSource));
		} catch (NonUniqueResultException e) {
			astronomicalObjectOptional = Optional.empty();
		}

		if (astronomicalObjectOptional.isPresent()) {
			return astronomicalObjectOptional;
		} else {
			Page page = pageApi.getPage(astronomicalObjectName, mediaWikiSource);
			if (page != null) {
				return astronomicalObjectRepository.findByPagePageIdAndPageMediaWikiSource(page.getPageId(),
						mediaWikiSourceMapper.fromSourcesToEntity(page.getMediaWikiSource()));
			}
		}

		return Optional.empty();
	}

}
