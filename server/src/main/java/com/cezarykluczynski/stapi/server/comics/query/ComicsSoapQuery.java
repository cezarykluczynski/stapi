package com.cezarykluczynski.stapi.server.comics.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest;
import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicsSoapQuery {

	private ComicsSoapMapper comicsSoapMapper;

	private PageMapper pageMapper;

	private ComicsRepository comicsRepository;

	@Inject
	public ComicsSoapQuery(ComicsSoapMapper comicsSoapMapper, PageMapper pageMapper, ComicsRepository comicsRepository) {
		this.comicsSoapMapper = comicsSoapMapper;
		this.pageMapper = pageMapper;
		this.comicsRepository = comicsRepository;
	}

	public Page<Comics> query(ComicsRequest comicsRequest) {
		ComicsRequestDTO comicsRequestDTO = comicsSoapMapper.map(comicsRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(comicsRequest.getPage());
		return comicsRepository.findMatching(comicsRequestDTO, pageRequest);
	}

}
