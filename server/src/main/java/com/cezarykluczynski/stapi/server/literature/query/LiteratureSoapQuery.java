package com.cezarykluczynski.stapi.server.literature.query;

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest;
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseSoapMapper;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LiteratureSoapQuery {

	private final LiteratureBaseSoapMapper literatureBaseSoapMapper;

	private final LiteratureFullSoapMapper literatureFullSoapMapper;

	private final PageMapper pageMapper;

	private final LiteratureRepository literatureRepository;

	public LiteratureSoapQuery(LiteratureBaseSoapMapper literatureBaseSoapMapper, LiteratureFullSoapMapper literatureFullSoapMapper,
			PageMapper pageMapper, LiteratureRepository literatureRepository) {
		this.literatureBaseSoapMapper = literatureBaseSoapMapper;
		this.literatureFullSoapMapper = literatureFullSoapMapper;
		this.pageMapper = pageMapper;
		this.literatureRepository = literatureRepository;
	}

	public Page<Literature> query(LiteratureBaseRequest literatureBaseRequest) {
		LiteratureRequestDTO literatureRequestDTO = literatureBaseSoapMapper.mapBase(literatureBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(literatureBaseRequest.getPage());
		return literatureRepository.findMatching(literatureRequestDTO, pageRequest);
	}

	public Page<Literature> query(LiteratureFullRequest literatureFullRequest) {
		LiteratureRequestDTO seriesRequestDTO = literatureFullSoapMapper.mapFull(literatureFullRequest);
		return literatureRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
