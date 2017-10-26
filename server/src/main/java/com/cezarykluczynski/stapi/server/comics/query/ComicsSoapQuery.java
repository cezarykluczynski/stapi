package com.cezarykluczynski.stapi.server.comics.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest;
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ComicsSoapQuery {

	private final ComicsBaseSoapMapper comicsBaseSoapMapper;

	private final ComicsFullSoapMapper comicsFullSoapMapper;

	private final PageMapper pageMapper;

	private final ComicsRepository comicsRepository;

	public ComicsSoapQuery(ComicsBaseSoapMapper comicsBaseSoapMapper, ComicsFullSoapMapper comicsFullSoapMapper, PageMapper pageMapper,
			ComicsRepository comicsRepository) {
		this.comicsBaseSoapMapper = comicsBaseSoapMapper;
		this.comicsFullSoapMapper = comicsFullSoapMapper;
		this.pageMapper = pageMapper;
		this.comicsRepository = comicsRepository;
	}

	public Page<Comics> query(ComicsBaseRequest comicsBaseRequest) {
		ComicsRequestDTO comicsRequestDTO = comicsBaseSoapMapper.mapBase(comicsBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(comicsBaseRequest.getPage());
		return comicsRepository.findMatching(comicsRequestDTO, pageRequest);
	}

	public Page<Comics> query(ComicsFullRequest comicsFullRequest) {
		ComicsRequestDTO comicsRequestDTO = comicsFullSoapMapper.mapFull(comicsFullRequest);
		return comicsRepository.findMatching(comicsRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
