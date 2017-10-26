package com.cezarykluczynski.stapi.server.element.query;

import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams;
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ElementRestQuery {

	private final ElementBaseRestMapper elementBaseRestMapper;

	private final PageMapper pageMapper;

	private final ElementRepository elementRepository;

	public ElementRestQuery(ElementBaseRestMapper elementBaseRestMapper, PageMapper pageMapper, ElementRepository elementRepository) {
		this.elementBaseRestMapper = elementBaseRestMapper;
		this.pageMapper = pageMapper;
		this.elementRepository = elementRepository;
	}

	public Page<Element> query(ElementRestBeanParams elementRestBeanParams) {
		ElementRequestDTO elementRequestDTO = elementBaseRestMapper.mapBase(elementRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(elementRestBeanParams);
		return elementRepository.findMatching(elementRequestDTO, pageRequest);
	}

}
