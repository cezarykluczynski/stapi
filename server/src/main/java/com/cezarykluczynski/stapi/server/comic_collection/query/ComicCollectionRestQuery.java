package com.cezarykluczynski.stapi.server.comic_collection.query;

import com.cezarykluczynski.stapi.model.comic_collection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comic_collection.repository.ComicCollectionRepository;
import com.cezarykluczynski.stapi.server.comic_collection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionRestQuery {

	private final ComicCollectionBaseRestMapper comicCollectionBaseRestMapper;

	private final PageMapper pageMapper;

	private final ComicCollectionRepository comicCollectionRepository;

	public ComicCollectionRestQuery(ComicCollectionBaseRestMapper comicCollectionBaseRestMapper, PageMapper pageMapper,
			ComicCollectionRepository comicCollectionRepository) {
		this.comicCollectionBaseRestMapper = comicCollectionBaseRestMapper;
		this.pageMapper = pageMapper;
		this.comicCollectionRepository = comicCollectionRepository;
	}

	public Page<ComicCollection> query(ComicCollectionRestBeanParams comicCollectionRestBeanParams) {
		ComicCollectionRequestDTO comicCollectionRequestDTO = comicCollectionBaseRestMapper.mapBase(comicCollectionRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(comicCollectionRestBeanParams);
		return comicCollectionRepository.findMatching(comicCollectionRequestDTO, pageRequest);
	}

}
