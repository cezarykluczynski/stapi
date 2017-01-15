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

	@Override
	public void enrich(EnrichablePair<CharacterboxTemplate, IndividualTemplate> enrichablePair) throws Exception {
		CharacterboxTemplate characterboxTemplate = enrichablePair.getInput();
		IndividualTemplate individualTemplate = enrichablePair.getOutput();

		individualTemplate.setGender(maybeUpdate(individualTemplate.getGender(), characterboxTemplate.getGender(), individualTemplate, "gender"));
		individualTemplate.setHeight(maybeUpdate(individualTemplate.getHeight(), characterboxTemplate.getHeight(), individualTemplate, "height"));
		individualTemplate.setWeight(maybeUpdate(individualTemplate.getWeight(), characterboxTemplate.getWeight(), individualTemplate, "weight"));
		individualTemplate.setMaritalStatus(maybeUpdate(individualTemplate.getMaritalStatus(), characterboxTemplate.getMaritalStatus(),
				individualTemplate, "marital status"));
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
