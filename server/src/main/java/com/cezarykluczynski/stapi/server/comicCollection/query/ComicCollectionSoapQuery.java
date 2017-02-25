package com.cezarykluczynski.stapi.server.comicCollection.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest;
import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comicCollection.repository.ComicCollectionRepository;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionSoapQuery {

	private ComicCollectionSoapMapper comicCollectionSoapMapper;

	private PageMapper pageMapper;

	private ComicCollectionRepository comicCollectionRepository;

	@Inject
	public ComicCollectionSoapQuery(ComicCollectionSoapMapper comicCollectionSoapMapper, PageMapper pageMapper,
			ComicCollectionRepository comicCollectionRepository) {
		this.comicCollectionSoapMapper = comicCollectionSoapMapper;
		this.pageMapper = pageMapper;
		this.comicCollectionRepository = comicCollectionRepository;
	}

	public Page<ComicCollection> query(ComicCollectionRequest comicCollectionRequest) {
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionSoapMapper.map(comicCollectionRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(comicCollectionRequest.getPage());
		return comicCollectionRepository.findMatching(comicCollectionRequestDTO, pageRequest);
	}

}
