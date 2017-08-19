package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipClassSpacecraftTypeProcessor implements ItemProcessor<String, SpacecraftType> {

	@Override
	public SpacecraftType process(String item) throws Exception {
		// TODO
		return null;
	}

}
