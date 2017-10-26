package com.cezarykluczynski.stapi.server.element.query;

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest;
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseSoapMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ElementSoapQuery {

	private final ElementBaseSoapMapper elementBaseSoapMapper;

	private final ElementFullSoapMapper elementFullSoapMapper;

	private final PageMapper pageMapper;

	private final ElementRepository elementRepository;

	public ElementSoapQuery(ElementBaseSoapMapper elementBaseSoapMapper, ElementFullSoapMapper elementFullSoapMapper, PageMapper pageMapper,
			ElementRepository elementRepository) {
		this.elementBaseSoapMapper = elementBaseSoapMapper;
		this.elementFullSoapMapper = elementFullSoapMapper;
		this.pageMapper = pageMapper;
		this.elementRepository = elementRepository;
	}

	public Page<Element> query(ElementBaseRequest elementBaseRequest) {
		ElementRequestDTO elementRequestDTO = elementBaseSoapMapper.mapBase(elementBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(elementBaseRequest.getPage());
		return elementRepository.findMatching(elementRequestDTO, pageRequest);
	}

	public Page<Element> query(ElementFullRequest elementFullRequest) {
		ElementRequestDTO seriesRequestDTO = elementFullSoapMapper.mapFull(elementFullRequest);
		return elementRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
