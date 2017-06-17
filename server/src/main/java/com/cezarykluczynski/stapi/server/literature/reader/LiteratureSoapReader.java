package com.cezarykluczynski.stapi.server.literature.reader;

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullResponse;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseSoapMapper;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullSoapMapper;
import com.cezarykluczynski.stapi.server.literature.query.LiteratureSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LiteratureSoapReader implements BaseReader<LiteratureBaseRequest, LiteratureBaseResponse>,
		FullReader<LiteratureFullRequest, LiteratureFullResponse> {

	private final LiteratureSoapQuery literatureSoapQuery;

	private final LiteratureBaseSoapMapper literatureBaseSoapMapper;

	private final LiteratureFullSoapMapper literatureFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public LiteratureSoapReader(LiteratureSoapQuery literatureSoapQuery, LiteratureBaseSoapMapper literatureBaseSoapMapper,
			LiteratureFullSoapMapper literatureFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.literatureSoapQuery = literatureSoapQuery;
		this.literatureBaseSoapMapper = literatureBaseSoapMapper;
		this.literatureFullSoapMapper = literatureFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public LiteratureBaseResponse readBase(LiteratureBaseRequest input) {
		Page<Literature> literaturePage = literatureSoapQuery.query(input);
		LiteratureBaseResponse literatureResponse = new LiteratureBaseResponse();
		literatureResponse.setPage(pageMapper.fromPageToSoapResponsePage(literaturePage));
		literatureResponse.setSort(sortMapper.map(input.getSort()));
		literatureResponse.getLiterature().addAll(literatureBaseSoapMapper.mapBase(literaturePage.getContent()));
		return literatureResponse;
	}

	@Override
	public LiteratureFullResponse readFull(LiteratureFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Literature> literaturePage = literatureSoapQuery.query(input);
		LiteratureFullResponse literatureFullResponse = new LiteratureFullResponse();
		literatureFullResponse.setLiterature(literatureFullSoapMapper.mapFull(Iterables.getOnlyElement(literaturePage.getContent(), null)));
		return literatureFullResponse;
	}

}
