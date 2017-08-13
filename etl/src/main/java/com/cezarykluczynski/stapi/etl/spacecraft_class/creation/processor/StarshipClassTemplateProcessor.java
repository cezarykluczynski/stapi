package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor;

import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipClassTemplateProcessor implements ItemProcessor<StarshipClassTemplate, SpacecraftClass> {

	@Override
	public SpacecraftClass process(StarshipClassTemplate item) throws Exception {
		// TODO
		return null;
	}

}
