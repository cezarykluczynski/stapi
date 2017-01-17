package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.util.tool.LogicUtil;
import liquibase.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class IndividualTemplateWithCharacterboxTemplateEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<CharacterboxTemplate, IndividualTemplate>> {

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
	public void enrich(EnrichablePair<CharacterboxTemplate, IndividualTemplate> enrichablePair) throws Exception {
		CharacterboxTemplate characterboxTemplate = enrichablePair.getInput();
		IndividualTemplate individualTemplate = enrichablePair.getOutput();

		individualTemplate.setGender(maybeUpdate(individualTemplate.getGender(), characterboxTemplate.getGender(), individualTemplate, GENDER));
		individualTemplate.setHeight(maybeUpdate(individualTemplate.getHeight(), characterboxTemplate.getHeight(), individualTemplate, HEIGHT));
		individualTemplate.setWeight(maybeUpdate(individualTemplate.getWeight(), characterboxTemplate.getWeight(), individualTemplate, WEIGHT));
		individualTemplate.setMaritalStatus(maybeUpdate(individualTemplate.getMaritalStatus(), characterboxTemplate.getMaritalStatus(),
				individualTemplate, MARITAL_STATUS));
		individualTemplate.setYearOfBirth(maybeUpdate(individualTemplate.getYearOfBirth(), characterboxTemplate.getYearOfBirth(),
				individualTemplate, YEAR_OF_BIRTH));
		individualTemplate.setMonthOfBirth(maybeUpdate(individualTemplate.getMonthOfBirth(), characterboxTemplate.getMonthOfBirth(),
				individualTemplate, MONTH_OF_BIRTH));
		individualTemplate.setDayOfBirth(maybeUpdate(individualTemplate.getDayOfBirth(), characterboxTemplate.getDayOfBirth(), individualTemplate,
				DAY_OF_BIRTH));
		individualTemplate.setPlaceOfBirth(maybeUpdate(individualTemplate.getPlaceOfBirth(), characterboxTemplate.getPlaceOfBirth(),
				individualTemplate, PLACE_OF_BIRTH));
		individualTemplate.setYearOfDeath(maybeUpdate(individualTemplate.getYearOfDeath(), characterboxTemplate.getYearOfDeath(),
				individualTemplate, YEAR_OF_DEATH));
		individualTemplate.setMonthOfDeath(maybeUpdate(individualTemplate.getMonthOfDeath(), characterboxTemplate.getMonthOfDeath(),
				individualTemplate, MONTH_OF_DEATH));
		individualTemplate.setDayOfDeath(maybeUpdate(individualTemplate.getDayOfDeath(), characterboxTemplate.getDayOfDeath(), individualTemplate,
				DAY_OF_DEATH));
		individualTemplate.setPlaceOfDeath(maybeUpdate(individualTemplate.getPlaceOfDeath(), characterboxTemplate.getPlaceOfDeath(),
				individualTemplate, PLACE_OF_DEATH));
	}

	private <T> T maybeUpdate(T baseValue, T newValue, IndividualTemplate individualTemplate, String valueName) {
		if (LogicUtil.xorNull(baseValue, newValue)) {
			return ObjectUtils.firstNonNull(baseValue, newValue);
		} else if (!Objects.equals(baseValue, newValue)) {
			log.warn("{} for {} differs: MA has {}, while MB has {}", StringUtils.upperCaseFirst(valueName), individualTemplate.getName(), baseValue,
					newValue);
			return baseValue;
		} else {
			return newValue;
		}
	}

}
