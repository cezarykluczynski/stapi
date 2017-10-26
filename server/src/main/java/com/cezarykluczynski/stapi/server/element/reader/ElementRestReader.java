package com.cezarykluczynski.stapi.server.element.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams;
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullRestMapper;
import com.cezarykluczynski.stapi.server.element.query.ElementRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ElementRestReader implements BaseReader<ElementRestBeanParams, ElementBaseResponse>, FullReader<String, ElementFullResponse> {

	private ElementRestQuery elementRestQuery;

	private ElementBaseRestMapper elementBaseRestMapper;

	private ElementFullRestMapper elementFullRestMapper;

	private PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ElementRestReader(ElementRestQuery elementRestQuery, ElementBaseRestMapper elementBaseRestMapper,
			ElementFullRestMapper elementFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.elementRestQuery = elementRestQuery;
		this.elementBaseRestMapper = elementBaseRestMapper;
		this.elementFullRestMapper = elementFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ElementBaseResponse readBase(ElementRestBeanParams input) {
		Page<Element> elementPage = elementRestQuery.query(input);
		ElementBaseResponse elementResponse = new ElementBaseResponse();
		elementResponse.setPage(pageMapper.fromPageToRestResponsePage(elementPage));
		elementResponse.setSort(sortMapper.map(input.getSort()));
		elementResponse.getElements().addAll(elementBaseRestMapper.mapBase(elementPage.getContent()));
		return elementResponse;
	}

	@Override
	public ElementFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ElementRestBeanParams elementRestBeanParams = new ElementRestBeanParams();
		elementRestBeanParams.setUid(uid);
		Page<Element> elementPage = elementRestQuery.query(elementRestBeanParams);
		ElementFullResponse elementResponse = new ElementFullResponse();
		elementResponse.setElement(elementFullRestMapper.mapFull(Iterables.getOnlyElement(elementPage.getContent(), null)));
		return elementResponse;
	}
}
