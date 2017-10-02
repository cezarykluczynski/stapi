package com.cezarykluczynski.stapi.etl.template.character.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate;
import com.cezarykluczynski.stapi.util.tool.LogicUtil;
import liquibase.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CharacterTemplateWithCharacterboxTemplateEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<CharacterboxTemplate, CharacterTemplate>> {

	private static final String GENDER = "gender";
	private static final String HEIGHT = "height";
	private static final String WEIGHT = "weight";
	private static final String MARITAL_STATUS = "marital status";
	private static final String YEAR_OF_BIRTH = "year of birth";
	private static final String MONTH_OF_BIRTH = "month of birth";
	private static final String DAY_OF_BIRTH = "day of birth";
	private static final String PLACE_OF_BIRTH = "place of birth";
	private static final String YEAR_OF_DEATH = "year of death";
	private static final String MONTH_OF_DEATH = "month of death";
	private static final String DAY_OF_DEATH = "day of death";
	private static final String PLACE_OF_DEATH = "place of death";

	@Override
	public void enrich(EnrichablePair<CharacterboxTemplate, CharacterTemplate> enrichablePair) throws Exception {
		CharacterboxTemplate characterboxTemplate = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		characterTemplate.setGender(maybeUpdate(characterTemplate.getGender(), characterboxTemplate.getGender(), characterTemplate, GENDER));
		characterTemplate.setHeight(maybeUpdate(characterTemplate.getHeight(), characterboxTemplate.getHeight(), characterTemplate, HEIGHT));
		characterTemplate.setWeight(maybeUpdate(characterTemplate.getWeight(), characterboxTemplate.getWeight(), characterTemplate, WEIGHT));
		characterTemplate.setMaritalStatus(maybeUpdate(characterTemplate.getMaritalStatus(), characterboxTemplate.getMaritalStatus(),
				characterTemplate, MARITAL_STATUS));
		characterTemplate.setYearOfBirth(maybeUpdate(characterTemplate.getYearOfBirth(), characterboxTemplate.getYearOfBirth(),
				characterTemplate, YEAR_OF_BIRTH));
		characterTemplate.setMonthOfBirth(maybeUpdate(characterTemplate.getMonthOfBirth(), characterboxTemplate.getMonthOfBirth(),
				characterTemplate, MONTH_OF_BIRTH));
		characterTemplate.setDayOfBirth(maybeUpdate(characterTemplate.getDayOfBirth(), characterboxTemplate.getDayOfBirth(), characterTemplate,
				DAY_OF_BIRTH));
		characterTemplate.setPlaceOfBirth(maybeUpdate(characterTemplate.getPlaceOfBirth(), characterboxTemplate.getPlaceOfBirth(),
				characterTemplate, PLACE_OF_BIRTH));
		characterTemplate.setYearOfDeath(maybeUpdate(characterTemplate.getYearOfDeath(), characterboxTemplate.getYearOfDeath(),
				characterTemplate, YEAR_OF_DEATH));
		characterTemplate.setMonthOfDeath(maybeUpdate(characterTemplate.getMonthOfDeath(), characterboxTemplate.getMonthOfDeath(),
				characterTemplate, MONTH_OF_DEATH));
		characterTemplate.setDayOfDeath(maybeUpdate(characterTemplate.getDayOfDeath(), characterboxTemplate.getDayOfDeath(), characterTemplate,
				DAY_OF_DEATH));
		characterTemplate.setPlaceOfDeath(maybeUpdate(characterTemplate.getPlaceOfDeath(), characterboxTemplate.getPlaceOfDeath(),
				characterTemplate, PLACE_OF_DEATH));
	}

	private <T> T maybeUpdate(T baseValue, T newValue, CharacterTemplate characterTemplate, String valueName) {
		if (LogicUtil.xorNull(baseValue, newValue)) {
			return ObjectUtils.firstNonNull(baseValue, newValue);
		} else if (!Objects.equals(baseValue, newValue)) {
			log.warn("{} for {} differs: MA has \"{}\", while MB has \"{}\"", StringUtils.upperCaseFirst(valueName), characterTemplate.getName(),
					baseValue, newValue);
			return baseValue;
		} else {
			return newValue;
		}
	}

}
