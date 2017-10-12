package com.cezarykluczynski.stapi.server.conflict.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullResponse;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseSoapMapper;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullSoapMapper;
import com.cezarykluczynski.stapi.server.conflict.query.ConflictSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ConflictSoapReader implements BaseReader<ConflictBaseRequest, ConflictBaseResponse>,
		FullReader<ConflictFullRequest, ConflictFullResponse> {

	private final ConflictSoapQuery conflictSoapQuery;

	private final ConflictBaseSoapMapper conflictBaseSoapMapper;

	private final ConflictFullSoapMapper conflictFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ConflictSoapReader(ConflictSoapQuery conflictSoapQuery, ConflictBaseSoapMapper conflictBaseSoapMapper,
			ConflictFullSoapMapper conflictFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.conflictSoapQuery = conflictSoapQuery;
		this.conflictBaseSoapMapper = conflictBaseSoapMapper;
		this.conflictFullSoapMapper = conflictFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ConflictBaseResponse readBase(ConflictBaseRequest input) {
		Page<Conflict> conflictPage = conflictSoapQuery.query(input);
		ConflictBaseResponse conflictResponse = new ConflictBaseResponse();
		conflictResponse.setPage(pageMapper.fromPageToSoapResponsePage(conflictPage));
		conflictResponse.setSort(sortMapper.map(input.getSort()));
		conflictResponse.getConflicts().addAll(conflictBaseSoapMapper.mapBase(conflictPage.getContent()));
		return conflictResponse;
	}

	@Override
	public ConflictFullResponse readFull(ConflictFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Conflict> conflictPage = conflictSoapQuery.query(input);
		ConflictFullResponse conflictFullResponse = new ConflictFullResponse();
		conflictFullResponse.setConflict(conflictFullSoapMapper.mapFull(Iterables.getOnlyElement(conflictPage.getContent(), null)));
		return conflictFullResponse;
	}

}
