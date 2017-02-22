package com.cezarykluczynski.stapi.server.comicStrip.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest;
import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comicStrip.repository.ComicStripRepository;
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripSoapQuery {

	private ComicStripSoapMapper comicStripSoapMapper;

	private PageMapper pageMapper;

	private ComicStripRepository comicStripRepository;

	@Inject
	public ComicStripSoapQuery(ComicStripSoapMapper comicStripSoapMapper, PageMapper pageMapper, ComicStripRepository comicStripRepository) {
		this.comicStripSoapMapper = comicStripSoapMapper;
		this.pageMapper = pageMapper;
		this.comicStripRepository = comicStripRepository;
	}

	public Page<ComicStrip> query(ComicStripRequest comicStripRequest) {
		ComicStripRequestDTO comicStripRequestDTO = comicStripSoapMapper.map(comicStripRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(comicStripRequest.getPage());
		return comicStripRepository.findMatching(comicStripRequestDTO, pageRequest);
	}

}
