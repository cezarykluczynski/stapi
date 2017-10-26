package com.cezarykluczynski.stapi.server.conflict.query;

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest;
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.model.conflict.repository.ConflictRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseSoapMapper;
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ConflictSoapQuery {

	private final ConflictBaseSoapMapper conflictBaseSoapMapper;

	private final ConflictFullSoapMapper conflictFullSoapMapper;

	private final PageMapper pageMapper;

	private final ConflictRepository conflictRepository;

	public ConflictSoapQuery(ConflictBaseSoapMapper conflictBaseSoapMapper, ConflictFullSoapMapper conflictFullSoapMapper,
			PageMapper pageMapper, ConflictRepository conflictRepository) {
		this.conflictBaseSoapMapper = conflictBaseSoapMapper;
		this.conflictFullSoapMapper = conflictFullSoapMapper;
		this.pageMapper = pageMapper;
		this.conflictRepository = conflictRepository;
	}

	public Page<Conflict> query(ConflictBaseRequest conflictBaseRequest) {
		ConflictRequestDTO conflictRequestDTO = conflictBaseSoapMapper.mapBase(conflictBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(conflictBaseRequest.getPage());
		return conflictRepository.findMatching(conflictRequestDTO, pageRequest);
	}

	public Page<Conflict> query(ConflictFullRequest conflictFullRequest) {
		ConflictRequestDTO conflictRequestDTO = conflictFullSoapMapper.mapFull(conflictFullRequest);
		return conflictRepository.findMatching(conflictRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
