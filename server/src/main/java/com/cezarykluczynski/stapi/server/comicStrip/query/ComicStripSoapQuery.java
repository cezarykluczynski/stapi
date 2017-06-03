package com.cezarykluczynski.stapi.server.comicStrip.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest;
import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comicStrip.repository.ComicStripRepository;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripSoapQuery {

	private final ComicStripBaseSoapMapper comicStripBaseSoapMapper;

	private final ComicStripFullSoapMapper comicStripFullSoapMapper;

	private final PageMapper pageMapper;

	private final ComicStripRepository comicStripRepository;

	@Inject
	public ComicStripSoapQuery(ComicStripBaseSoapMapper comicStripBaseSoapMapper, ComicStripFullSoapMapper comicStripFullSoapMapper,
			PageMapper pageMapper, ComicStripRepository comicStripRepository) {
		this.comicStripBaseSoapMapper = comicStripBaseSoapMapper;
		this.comicStripFullSoapMapper = comicStripFullSoapMapper;
		this.pageMapper = pageMapper;
		this.comicStripRepository = comicStripRepository;
	}

	public Page<ComicStrip> query(ComicStripBaseRequest comicStripBaseRequest) {
		ComicStripRequestDTO comicStripRequestDTO = comicStripBaseSoapMapper.mapBase(comicStripBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(comicStripBaseRequest.getPage());
		return comicStripRepository.findMatching(comicStripRequestDTO, pageRequest);
	}

	public Page<ComicStrip> query(ComicStripFullRequest comicStripFullRequest) {
		ComicStripRequestDTO comicStripRequestDTO = comicStripFullSoapMapper.mapFull(comicStripFullRequest);
		return comicStripRepository.findMatching(comicStripRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
