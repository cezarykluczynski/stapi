package com.cezarykluczynski.stapi.server.element.reader;

import com.cezarykluczynski.stapi.client.rest.model.ElementV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.ElementV2FullResponse;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.element.dto.ElementV2RestBeanParams;
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper;
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullRestMapper;
import com.cezarykluczynski.stapi.server.element.query.ElementRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ElementV2RestReader implements BaseReader<ElementV2RestBeanParams, ElementV2BaseResponse>,
		FullReader<ElementV2FullResponse> {

	private final ElementRestQuery elementRestQuery;

	private final ElementBaseRestMapper elementBaseRestMapper;

	private final ElementFullRestMapper elementFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ElementV2RestReader(ElementRestQuery elementRestQuery, ElementBaseRestMapper elementBaseRestMapper,
								ElementFullRestMapper elementFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.elementRestQuery = elementRestQuery;
		this.elementBaseRestMapper = elementBaseRestMapper;
		this.elementFullRestMapper = elementFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ElementV2BaseResponse readBase(ElementV2RestBeanParams input) {
		Page<Element> elementV2Page = elementRestQuery.query(input);
		ElementV2BaseResponse elementV2Response = new ElementV2BaseResponse();
		elementV2Response.setPage(pageMapper.fromPageToRestResponsePage(elementV2Page));
		elementV2Response.setSort(sortMapper.map(input.getSort()));
		elementV2Response.getElements().addAll(elementBaseRestMapper.mapV2Base(elementV2Page.getContent()));
		return elementV2Response;
	}

	@Override
	public ElementV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ElementV2RestBeanParams elementV2RestBeanParams = new ElementV2RestBeanParams();
		elementV2RestBeanParams.setUid(uid);
		Page<Element> elementPage = elementRestQuery.query(elementV2RestBeanParams);
		ElementV2FullResponse elementV2Response = new ElementV2FullResponse();
		elementV2Response.setElement(elementFullRestMapper.mapV2Full(Iterables.getOnlyElement(elementPage.getContent(), null)));
		return elementV2Response;
	}
}
