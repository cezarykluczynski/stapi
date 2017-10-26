package com.cezarykluczynski.stapi.server.literature.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFullResponse;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.literature.dto.LiteratureRestBeanParams;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseRestMapper;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullRestMapper;
import com.cezarykluczynski.stapi.server.literature.query.LiteratureRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LiteratureRestReader implements BaseReader<LiteratureRestBeanParams, LiteratureBaseResponse>,
		FullReader<String, LiteratureFullResponse> {

	private final LiteratureRestQuery literatureRestQuery;

	private final LiteratureBaseRestMapper literatureBaseRestMapper;

	private final LiteratureFullRestMapper literatureFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public LiteratureRestReader(LiteratureRestQuery literatureRestQuery, LiteratureBaseRestMapper literatureBaseRestMapper,
			LiteratureFullRestMapper literatureFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.literatureRestQuery = literatureRestQuery;
		this.literatureBaseRestMapper = literatureBaseRestMapper;
		this.literatureFullRestMapper = literatureFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public LiteratureBaseResponse readBase(LiteratureRestBeanParams input) {
		Page<Literature> literaturePage = literatureRestQuery.query(input);
		LiteratureBaseResponse literatureResponse = new LiteratureBaseResponse();
		literatureResponse.setPage(pageMapper.fromPageToRestResponsePage(literaturePage));
		literatureResponse.setSort(sortMapper.map(input.getSort()));
		literatureResponse.getLiterature().addAll(literatureBaseRestMapper.mapBase(literaturePage.getContent()));
		return literatureResponse;
	}

	@Override
	public LiteratureFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		LiteratureRestBeanParams literatureRestBeanParams = new LiteratureRestBeanParams();
		literatureRestBeanParams.setUid(uid);
		Page<Literature> literaturePage = literatureRestQuery.query(literatureRestBeanParams);
		LiteratureFullResponse literatureResponse = new LiteratureFullResponse();
		literatureResponse.setLiterature(literatureFullRestMapper.mapFull(Iterables.getOnlyElement(literaturePage.getContent(), null)));
		return literatureResponse;
	}
}
