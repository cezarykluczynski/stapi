package com.cezarykluczynski.stapi.etl.template.individual.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IndividualHeightProcessor implements ItemProcessor<String, Integer> {

	@Override
	public Integer process(String item) throws Exception {
		// TODO
		if (StringUtils.isNotBlank(item)) {
			log.info("Height value is :" + item);
		}
		return null;
	}
}
