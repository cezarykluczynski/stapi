package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class IndividualBloodTypeProcessor implements ItemProcessor<String, BloodType> {

	private static final String B_NEGATIVE = "b-negative";
	private static final String O_NEGATIVE = "o-negative";
	private static final String T_NEGATIVE = "t-negative";

	private static final Map<String, BloodType> bloodTypeMappings = Maps.newLinkedHashMap();

	static {
		bloodTypeMappings.put(B_NEGATIVE, BloodType.B_NEGATIVE);
		bloodTypeMappings.put(O_NEGATIVE, BloodType.O_NEGATIVE);
		bloodTypeMappings.put(T_NEGATIVE, BloodType.T_NEGATIVE);
	}

	@Override
	public BloodType process(String item) throws Exception {
		item = StringUtils.lowerCase(StringUtils.trim(item));

		if (StringUtils.isBlank(item)) {
			return null;
		}

		for (Map.Entry<String, BloodType> entry : bloodTypeMappings.entrySet()) {
			if (item.contains(entry.getKey())) {
				return entry.getValue();
			}
		}

		log.error("Unknown individual blood type: {} ", item);
		return null;
	}

}
