package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.template.starship_class.dto.ActivityPeriodDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipClassActivityPeriodProcessor implements ItemProcessor<String, ActivityPeriodDTO> {

	@Override
	public ActivityPeriodDTO process(String item) throws Exception {
		return null;
	}

}
