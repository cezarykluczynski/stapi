package com.cezarykluczynski.stapi.server.conflict.query;

import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.model.conflict.repository.ConflictRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ConflictRestQuery {

	private final ConflictBaseRestMapper conflictBaseRestMapper;

	private final PageMapper pageMapper;

	private final ConflictRepository conflictRepository;

	public ConflictRestQuery(ConflictBaseRestMapper conflictBaseRestMapper, PageMapper pageMapper, ConflictRepository conflictRepository) {
		this.conflictBaseRestMapper = conflictBaseRestMapper;
		this.pageMapper = pageMapper;
		this.conflictRepository = conflictRepository;
	}

	public Page<Conflict> query(ConflictRestBeanParams conflictRestBeanParams) {
		ConflictRequestDTO conflictRequestDTO = conflictBaseRestMapper.mapBase(conflictRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(conflictRestBeanParams);
		return conflictRepository.findMatching(conflictRequestDTO, pageRequest);
	}

}
