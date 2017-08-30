package com.cezarykluczynski.stapi.etl.template.starship.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipStatusProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		// TODO
		return null;
	}

}
