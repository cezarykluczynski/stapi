package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor;

import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackTemplateProcessor implements ItemProcessor<SoundtrackTemplate, Soundtrack> {

	@Override
	public Soundtrack process(SoundtrackTemplate item) throws Exception {
		// TODO
		return null;
	}

}
