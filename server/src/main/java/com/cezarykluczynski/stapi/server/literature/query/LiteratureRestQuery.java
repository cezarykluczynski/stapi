package com.cezarykluczynski.stapi.server.literature.query;

import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.literature.dto.LiteratureRestBeanParams;
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LiteratureRestQuery {

	private final LiteratureBaseRestMapper literatureBaseRestMapper;

	private final PageMapper pageMapper;

	private final LiteratureRepository literatureRepository;

	public LiteratureRestQuery(LiteratureBaseRestMapper literatureBaseRestMapper, PageMapper pageMapper, LiteratureRepository literatureRepository) {
		this.literatureBaseRestMapper = literatureBaseRestMapper;
		this.pageMapper = pageMapper;
		this.literatureRepository = literatureRepository;
	}

	public Page<Literature> query(LiteratureRestBeanParams literatureRestBeanParams) {
		LiteratureRequestDTO literatureRequestDTO = literatureBaseRestMapper.mapBase(literatureRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(literatureRestBeanParams);
		return literatureRepository.findMatching(literatureRequestDTO, pageRequest);
	}

}
