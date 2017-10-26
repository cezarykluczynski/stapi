package com.cezarykluczynski.stapi.server.conflict.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse;
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
public class ConflictRestReader implements BaseReader<ConflictRestBeanParams, ConflictBaseResponse>,
		FullReader<String, ConflictFullResponse> {

	private final ConflictRestQuery conflictRestQuery;

	private final ConflictBaseRestMapper conflictBaseRestMapper;

	private final ConflictFullRestMapper conflictFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ConflictRestReader(ConflictRestQuery conflictRestQuery, ConflictBaseRestMapper conflictBaseRestMapper,
			ConflictFullRestMapper conflictFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.conflictRestQuery = conflictRestQuery;
		this.conflictBaseRestMapper = conflictBaseRestMapper;
		this.conflictFullRestMapper = conflictFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ConflictBaseResponse readBase(ConflictRestBeanParams input) {
		Page<Conflict> conflictPage = conflictRestQuery.query(input);
		ConflictBaseResponse conflictResponse = new ConflictBaseResponse();
		conflictResponse.setPage(pageMapper.fromPageToRestResponsePage(conflictPage));
		conflictResponse.setSort(sortMapper.map(input.getSort()));
		conflictResponse.getConflicts().addAll(conflictBaseRestMapper.mapBase(conflictPage.getContent()));
		return conflictResponse;
	}

	@Override
	public ConflictFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ConflictRestBeanParams conflictRestBeanParams = new ConflictRestBeanParams();
		conflictRestBeanParams.setUid(uid);
		Page<Conflict> conflictPage = conflictRestQuery.query(conflictRestBeanParams);
		ConflictFullResponse conflictResponse = new ConflictFullResponse();
		conflictResponse.setConflict(conflictFullRestMapper.mapFull(Iterables.getOnlyElement(conflictPage.getContent(), null)));
		return conflictResponse;
	}

}
