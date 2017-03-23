package com.cezarykluczynski.stapi.server.comicCollection.query;

import com.cezarykluczynski.stapi.model.comicCollection.dto.ComicCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comicCollection.repository.ComicCollectionRepository;
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionRestQuery {

	private ComicCollectionBaseRestMapper comicCollectionBaseRestMapper;

	private PageMapper pageMapper;

	private ComicCollectionRepository comicCollectionRepository;

	@Inject
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
