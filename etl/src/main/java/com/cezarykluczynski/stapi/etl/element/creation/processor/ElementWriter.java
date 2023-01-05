package com.cezarykluczynski.stapi.etl.element.creation.processor;

import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ElementWriter implements ItemWriter<Element> {

	private final ElementRepository elementRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public ElementWriter(ElementRepository elementRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.elementRepository = elementRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(Chunk<? extends Element> items) throws Exception {
		elementRepository.saveAll(process(items));
	}

	private List<Element> process(Chunk<? extends Element> elementList) {
		List<Element> elementListWithoutExtends = fromExtendsListToElementList(elementList);
		return filterDuplicates(elementListWithoutExtends);
	}

	private List<Element> fromExtendsListToElementList(Chunk<? extends Element> elementList) {
		return elementList
				.getItems()
				.stream()
				.map(pageAware -> (Element) pageAware)
				.collect(Collectors.toList());
	}

	private List<Element> filterDuplicates(List<Element> elementList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(elementList.stream()
				.map(element -> (PageAware) element)
				.collect(Collectors.toList()), Element.class).stream()
				.map(pageAware -> (Element) pageAware)
				.collect(Collectors.toList());
	}

}
