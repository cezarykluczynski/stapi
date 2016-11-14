package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.model.common.entity.MaritalStatus;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class IndividualMaritalStatusProcessor implements ItemProcessor<String, MaritalStatus> {

	private static final String SINGLE = "single";
	private static final String WIDOW = "widow";
	private static final String ENGAGED = "engaged";
	private static final String DIVORCED = "divorced";
	private static final String SEPARATED = "separated";
	private static final String CAPTAINS_WOMAN = "captain's woman";
	private static final String ANNULLED = "annulled";
	private static final String REMARRIED = "remarried";
	private static final String MARRIED = "married";
	private static final String BONDED = "bonded";

	private static final Map<String, MaritalStatus> maritalStatusMappings = Maps.newLinkedHashMap();

	static {
		maritalStatusMappings.put(SINGLE, MaritalStatus.SINGLE);
		maritalStatusMappings.put(ANNULLED, MaritalStatus.SINGLE);
		maritalStatusMappings.put(WIDOW, MaritalStatus.WIDOWED);
		maritalStatusMappings.put(ENGAGED, MaritalStatus.ENGAGED);
		maritalStatusMappings.put(DIVORCED, MaritalStatus.DIVORCED);
		maritalStatusMappings.put(SEPARATED, MaritalStatus.SEPARATED);
		maritalStatusMappings.put(REMARRIED, MaritalStatus.REMARRIED);
		maritalStatusMappings.put(MARRIED, MaritalStatus.MARRIED);
		maritalStatusMappings.put(BONDED, MaritalStatus.MARRIED);
		maritalStatusMappings.put(CAPTAINS_WOMAN, MaritalStatus.CAPTAINS_WOMAN);
	}

	@Override
	public MaritalStatus process(String item) throws Exception {
		item = StringUtils.lowerCase(StringUtils.trim(item));

		if (StringUtils.isBlank(item)) {
			return null;
		}

		for (Map.Entry<String, MaritalStatus> entry : maritalStatusMappings.entrySet()) {
			if (item.contains(entry.getKey())) {
				return entry.getValue();
			}
		}

		log.error("Unknown individual marital status: {} ", item);
		return null;
	}

}
