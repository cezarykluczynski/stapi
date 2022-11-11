package com.cezarykluczynski.stapi.etl.movie.creation.service;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieExistingEntitiesHelper {

	private final CharacterRepository characterRepository;

	private final PerformerRepository performerRepository;

	private final CompanyRepository companyRepository;

	private final List<String> characterNames = Lists.newArrayList();

	private final List<String> performerNames = Lists.newArrayList();

	private final List<String> companyNames = Lists.newArrayList();

	public void initialize() {
		characterNames.addAll(characterRepository.findAll().stream()
				.map(Character::getName)
				.collect(Collectors.toList()));
		performerNames.addAll(performerRepository.findAll().stream()
				.map(RealWorldPerson::getName)
				.collect(Collectors.toList()));
		companyNames.addAll(companyRepository.findAll().stream()
				.map(Company::getName)
				.collect(Collectors.toList()));
	}

	public boolean isKnownCharacter(String characterName) {
		return characterName != null && characterNames.contains(characterName);
	}

	public boolean isKnownPerformer(String performerName) {
		return performerName != null && performerNames.contains(performerName);
	}

	public boolean isKnownCompany(String companyName) {
		return companyName != null && companyNames.contains(companyName);
	}

	public boolean isAnyKnownCharacter(String... characters) {
		return Arrays.stream(characters).anyMatch(this::isKnownCharacter);
	}

	public boolean isAnyKnownPerformer(String... performers) {
		return Arrays.stream(performers).anyMatch(this::isKnownPerformer);
	}
}
