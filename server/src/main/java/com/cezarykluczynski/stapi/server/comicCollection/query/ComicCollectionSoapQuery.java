package com.cezarykluczynski.stapi.server.comicCollection.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest;
import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comicCollection.repository.ComicCollectionRepository;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionSoapQuery {

	private ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper;

	private ComicCollectionFullSoapMapper comicCollectionFullSoapMapper;

	private PageMapper pageMapper;

	private ComicCollectionRepository comicCollectionRepository;

	@Inject
	public ComicCollectionSoapQuery(ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper,
			ComicCollectionFullSoapMapper comicCollectionFullSoapMapper, PageMapper pageMapper, ComicCollectionRepository comicCollectionRepository) {
		this.comicCollectionBaseSoapMapper = comicCollectionBaseSoapMapper;
		this.comicCollectionFullSoapMapper = comicCollectionFullSoapMapper;
		this.pageMapper = pageMapper;
		this.comicCollectionRepository = comicCollectionRepository;
	}

	public Page<ComicCollection> query(ComicCollectionBaseRequest comicCollectionBaseRequest) {
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionBaseSoapMapper.mapBase(comicCollectionBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(comicCollectionBaseRequest.getPage());
		return comicCollectionRepository.findMatching(comicCollectionRequestDTO, pageRequest);
	}

	public Page<ComicCollection> query(ComicCollectionFullRequest comicCollectionFullRequest) {
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionFullSoapMapper.mapFull(comicCollectionFullRequest);
		return comicCollectionRepository.findMatching(comicCollectionRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
