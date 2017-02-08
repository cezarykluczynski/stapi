package com.cezarykluczynski.stapi.etl.comics.creation.processor;

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicsTemplateProcessor implements ItemProcessor<ComicsTemplate, Comics> {

	private GuidGenerator guidGenerator;

	@Inject
	public ComicsTemplateProcessor(GuidGenerator guidGenerator) {
		this.guidGenerator = guidGenerator;
	}

	@Override
	public Comics process(ComicsTemplate item) throws Exception {
		Comics comics = new Comics();
		// TODO
		return comics;

	}

}
