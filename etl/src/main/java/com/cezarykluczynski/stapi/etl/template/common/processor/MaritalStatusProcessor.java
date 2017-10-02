package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MaritalStatusProcessor implements ItemProcessor<String, MaritalStatus> {

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

	private static final Map<String, MaritalStatus> MARITAL_STATUS_MAPPINGS = Maps.newLinkedHashMap();

	static {
		MARITAL_STATUS_MAPPINGS.put(SINGLE, MaritalStatus.SINGLE);
		MARITAL_STATUS_MAPPINGS.put(ANNULLED, MaritalStatus.SINGLE);
		MARITAL_STATUS_MAPPINGS.put(WIDOW, MaritalStatus.WIDOWED);
		MARITAL_STATUS_MAPPINGS.put(ENGAGED, MaritalStatus.ENGAGED);
		MARITAL_STATUS_MAPPINGS.put(DIVORCED, MaritalStatus.DIVORCED);
		MARITAL_STATUS_MAPPINGS.put(SEPARATED, MaritalStatus.SEPARATED);
		MARITAL_STATUS_MAPPINGS.put(REMARRIED, MaritalStatus.REMARRIED);
		MARITAL_STATUS_MAPPINGS.put(MARRIED, MaritalStatus.MARRIED);
		MARITAL_STATUS_MAPPINGS.put(BONDED, MaritalStatus.MARRIED);
		MARITAL_STATUS_MAPPINGS.put(CAPTAINS_WOMAN, MaritalStatus.CAPTAINS_WOMAN);
	}

	@Override
	public MaritalStatus process(String item) throws Exception {
		String itemTrimmedLowerCase = StringUtils.lowerCase(StringUtils.trim(item));

		if (StringUtils.isBlank(itemTrimmedLowerCase)) {
			return null;
		}

		for (Map.Entry<String, MaritalStatus> entry : MARITAL_STATUS_MAPPINGS.entrySet()) {
			if (itemTrimmedLowerCase.contains(entry.getKey())) {
				return entry.getValue();
			}
		}

		log.info("Unknown character marital status: \"{}\"", itemTrimmedLowerCase);
		return null;
	}

}
