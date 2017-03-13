package com.cezarykluczynski.stapi.server.comicCollection.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionResponse;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.query.ComicCollectionRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionRestReader implements BaseReader<ComicCollectionRestBeanParams, ComicCollectionResponse> {

	private ComicCollectionRestQuery comicCollectionRestQuery;

	private ComicCollectionRestMapper comicCollectionRestMapper;

	private PageMapper pageMapper;

	@Inject
	public ComicCollectionRestReader(ComicCollectionRestQuery comicCollectionRestQuery, ComicCollectionRestMapper comicCollectionRestMapper,
			PageMapper pageMapper) {
		this.comicCollectionRestQuery = comicCollectionRestQuery;
		this.comicCollectionRestMapper = comicCollectionRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicCollectionResponse readBase(ComicCollectionRestBeanParams input) {
		Page<ComicCollection> comicCollectionPage = comicCollectionRestQuery.query(input);
		ComicCollectionResponse comicCollectionResponse = new ComicCollectionResponse();
		comicCollectionResponse.setPage(pageMapper.fromPageToRestResponsePage(comicCollectionPage));
		comicCollectionResponse.getComicCollections().addAll(comicCollectionRestMapper.map(comicCollectionPage.getContent()));
		return comicCollectionResponse;
	}

}
