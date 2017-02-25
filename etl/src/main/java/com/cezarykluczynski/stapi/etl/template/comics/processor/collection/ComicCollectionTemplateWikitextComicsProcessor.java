package com.cezarykluczynski.stapi.etl.template.comics.processor.collection;

import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ComicCollectionTemplateWikitextComicsProcessor implements ItemProcessor<String, Set<Comics>> {

	@Override
	public Set<Comics> process(String item) throws Exception {
		// TODO
		return null;
	}
}
