package com.cezarykluczynski.stapi.server.conflict.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2FullResponse;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullRestMapper;
import com.cezarykluczynski.stapi.server.conflict.query.ConflictRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ConflictV2RestReader implements FullReader<String, ConflictV2FullResponse> {

	private final ConflictRestQuery conflictRestQuery;

	private final ConflictFullRestMapper conflictFullRestMapper;

	public ConflictV2RestReader(ConflictRestQuery conflictRestQuery, ConflictFullRestMapper conflictFullRestMapper) {
		this.conflictRestQuery = conflictRestQuery;
		this.conflictFullRestMapper = conflictFullRestMapper;
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
