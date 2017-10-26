package com.cezarykluczynski.stapi.server.title.query;

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest;
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseSoapMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TitleSoapQuery {

	private final TitleBaseSoapMapper titleBaseSoapMapper;

	private final TitleFullSoapMapper titleFullSoapMapper;

	private final PageMapper pageMapper;

	private final TitleRepository titleRepository;

	public TitleSoapQuery(TitleBaseSoapMapper titleBaseSoapMapper, TitleFullSoapMapper titleFullSoapMapper, PageMapper pageMapper,
			TitleRepository titleRepository) {
		this.titleBaseSoapMapper = titleBaseSoapMapper;
		this.titleFullSoapMapper = titleFullSoapMapper;
		this.pageMapper = pageMapper;
		this.titleRepository = titleRepository;
	}

	public Page<Title> query(TitleBaseRequest titleBaseRequest) {
		TitleRequestDTO titleRequestDTO = titleBaseSoapMapper.mapBase(titleBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(titleBaseRequest.getPage());
		return titleRepository.findMatching(titleRequestDTO, pageRequest);
	}

	public Page<Title> query(TitleFullRequest titleFullRequest) {
		TitleRequestDTO seriesRequestDTO = titleFullSoapMapper.mapFull(titleFullRequest);
		return titleRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
