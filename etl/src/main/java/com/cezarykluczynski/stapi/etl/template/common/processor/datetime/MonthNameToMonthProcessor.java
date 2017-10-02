package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.Month;

@Service
@Slf4j
public class MonthNameToMonthProcessor implements ItemProcessor<String, Month> {

	@Override
	public Month process(String item) throws Exception {
		String monthNameUpperCase = StringUtils.upperCase(item);
		try {
			return Month.valueOf(monthNameUpperCase);
		} catch (IllegalArgumentException e) {
			log.info("{} could not be mapped to Month.", monthNameUpperCase);
			return null;
		}
	}

}
