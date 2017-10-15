package com.cezarykluczynski.stapi.server.element.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullResponse;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseSoapMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullSoapMapper;
import com.cezarykluczynski.stapi.server.element.query.ElementSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ElementSoapReader implements BaseReader<ElementBaseRequest, ElementBaseResponse>, FullReader<ElementFullRequest, ElementFullResponse> {

	private final ElementSoapQuery elementSoapQuery;

	private final ElementBaseSoapMapper elementBaseSoapMapper;

	private final ElementFullSoapMapper elementFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ElementSoapReader(ElementSoapQuery elementSoapQuery, ElementBaseSoapMapper elementBaseSoapMapper,
			ElementFullSoapMapper elementFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.elementSoapQuery = elementSoapQuery;
		this.elementBaseSoapMapper = elementBaseSoapMapper;
		this.elementFullSoapMapper = elementFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ElementBaseResponse readBase(ElementBaseRequest input) {
		Page<Element> elementPage = elementSoapQuery.query(input);
		ElementBaseResponse elementResponse = new ElementBaseResponse();
		elementResponse.setPage(pageMapper.fromPageToSoapResponsePage(elementPage));
		elementResponse.setSort(sortMapper.map(input.getSort()));
		elementResponse.getElements().addAll(elementBaseSoapMapper.mapBase(elementPage.getContent()));
		return elementResponse;
	}

	@Override
	public ElementFullResponse readFull(ElementFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Element> elementPage = elementSoapQuery.query(input);
		ElementFullResponse elementFullResponse = new ElementFullResponse();
		elementFullResponse.setElement(elementFullSoapMapper.mapFull(Iterables.getOnlyElement(elementPage.getContent(), null)));
		return elementFullResponse;
	}

}
