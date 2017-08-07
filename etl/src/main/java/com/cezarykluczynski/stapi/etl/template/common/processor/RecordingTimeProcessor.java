package com.cezarykluczynski.stapi.etl.template.common.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class RecordingTimeProcessor implements ItemProcessor<String, Integer> {

	@Override
	public Integer process(String item) throws Exception {
		return null;
	}

}
