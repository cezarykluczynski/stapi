package com.cezarykluczynski.stapi.etl.element.creation.processor;

import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElementWriter implements ItemWriter<Element> {

	private final ElementRepository elementRepository;

	public ElementWriter(ElementRepository elementRepository) {
		this.elementRepository = elementRepository;
	}

	@Override
	public void write(Chunk<? extends Element> items) throws Exception {
		elementRepository.saveAll(items.getItems());
	}

}
