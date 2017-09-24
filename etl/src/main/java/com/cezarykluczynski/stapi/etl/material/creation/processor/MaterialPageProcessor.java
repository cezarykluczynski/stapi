package com.cezarykluczynski.stapi.etl.material.creation.processor;

import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MaterialPageProcessor implements ItemProcessor<Page, Material> {

	@Override
	public Material process(Page item) throws Exception {
		// TODO
		return null;
	}

}
