package com.cezarykluczynski.stapi.server.comicCollection.query;

import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comicCollection.repository.ComicCollectionRepository;
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionRestQuery {

	private ComicCollectionRestMapper comicCollectionRestMapper;

	private PageMapper pageMapper;

	private ComicCollectionRepository comicCollectionRepository;

	@Inject
	public ComicCollectionRestQuery(ComicCollectionRestMapper comicCollectionRestMapper, PageMapper pageMapper,
			ComicCollectionRepository comicCollectionRepository) {
		this.comicCollectionRestMapper = comicCollectionRestMapper;
		this.pageMapper = pageMapper;
		this.comicCollectionRepository = comicCollectionRepository;
	}

	public Page<ComicCollection> query(ComicCollectionRestBeanParams comicCollectionRestBeanParams) {
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionRestMapper.map(comicCollectionRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(comicCollectionRestBeanParams);
		return comicCollectionRepository.findMatching(comicCollectionRequestDTO, pageRequest);
	}

}
