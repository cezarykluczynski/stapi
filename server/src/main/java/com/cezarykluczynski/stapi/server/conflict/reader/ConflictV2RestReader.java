package com.cezarykluczynski.stapi.server.conflict.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2FullResponse;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseRestMapper;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullRestMapper;
import com.cezarykluczynski.stapi.server.conflict.query.ConflictRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ConflictV2RestReader implements BaseReader<ConflictRestBeanParams, ConflictV2BaseResponse>,
		FullReader<String, ConflictV2FullResponse> {

	private final ConflictRestQuery conflictRestQuery;

	private final ConflictBaseRestMapper conflictBaseRestMapper;

	private final ConflictFullRestMapper conflictFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ConflictV2RestReader(ConflictRestQuery conflictRestQuery, ConflictBaseRestMapper conflictBaseRestMapper,
			ConflictFullRestMapper conflictFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.conflictRestQuery = conflictRestQuery;
		this.conflictBaseRestMapper = conflictBaseRestMapper;
		this.conflictFullRestMapper = conflictFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ConflictV2BaseResponse readBase(ConflictRestBeanParams input) {
		Page<Conflict> conflictV2Page = conflictRestQuery.query(input);
		ConflictV2BaseResponse conflictV2Response = new ConflictV2BaseResponse();
		conflictV2Response.setPage(pageMapper.fromPageToRestResponsePage(conflictV2Page));
		conflictV2Response.setSort(sortMapper.map(input.getSort()));
		conflictV2Response.getConflicts().addAll(conflictBaseRestMapper.mapV2Base(conflictV2Page.getContent()));
		return conflictV2Response;
	}

	@Override
	public ConflictV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ConflictRestBeanParams conflictRestBeanParams = new ConflictRestBeanParams();
		conflictRestBeanParams.setUid(uid);
		Page<Conflict> conflictPage = conflictRestQuery.query(conflictRestBeanParams);
		ConflictV2FullResponse conflictV2Response = new ConflictV2FullResponse();
		conflictV2Response.setConflict(conflictFullRestMapper.mapV2Full(Iterables.getOnlyElement(conflictPage.getContent(), null)));
		return conflictV2Response;
	}

}
